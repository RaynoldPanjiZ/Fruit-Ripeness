const http = require('http');
const Firestore = require('@google-cloud/firestore');

const db = new Firestore({
	projectId: 'capstone-praject-b21-cap0293',
	keyFilename: './capstone-praject-b21-cap0293-b706e79739bb.json',
});

 
const requestListener = (request, response) => {
    response.setHeader('Content-Type', 'application/json');
    const { method, url } = request;
    const search = url.match(new RegExp('/search/(.*)((/)|())'));

    if(url === '/') {
    	response.statusCode = 200;
        response.end(JSON.stringify({
            message: `Halaman tidak tersedia`
        }));
    } else if (url === '/upload' || url === '/upload/') {
        if(method === 'POST') {
			let body = [];

			request.on('data', (chunk) => {
				body.push(chunk);
			});

			request.on('end', () => {
				body = Buffer.concat(body).toString();
				response.end(body);
			});
		} else {
            response.statusCode = 400;
            response.end(JSON.stringify({
                message: `Halaman tidak ditemukan`,
            }));
        }
    } else if(url === '/getAll' || url === '/getAll/') {
        if(method === 'GET') {
            response.statusCode = 200;
			db.collection('info_buah').get().then(function(querySnapshot) {
				var data = new Array();
				querySnapshot.forEach(function(doc) {
		            data.push(doc.data());
		        });
				response.end(JSON.stringify({ 
					message : 'Success',
					data 
				}));
			});
        } else {
            response.statusCode = 400;
            response.end(JSON.stringify({
                message: `Halaman tidak ditemukan`,
            }));
        }
    } else if(search) {
        if(method === 'GET') {
            response.statusCode = 200;
			const param = search[1].replace('/','');;
			
			db.collection('info_buah').get().then(function(querySnapshot) {
				var data = "";
				querySnapshot.forEach(function(doc) {
				var test = doc.data()['name'];
					if (doc.data()['name'].toLowerCase() === param.toLowerCase()) {
		            	data = doc.data();
		            	response.end(JSON.stringify({ 
							message : 'Success',
							data 
						}));
					}
		        });
				if (data == "") {
					response.end(JSON.stringify({
	            		message : 'Maaf data yang anda cari tidak tersedia',
	            	}));    
	            }
			});
			
        } else {
            response.statusCode = 400;
            response.end(JSON.stringify({
                message: `Data tidak cocok`,
            }));
        }
    } else {
            const clienturl = request.url;
        response.statusCode = 400;
        response.end(JSON.stringify({
            message: `Halaman tidak tersedia`
        }));
    }
    
};
 
 
const server = http.createServer(requestListener);
 
const port = 5000;
const host = 'localhost';
 
server.listen(port, host, () => {
    console.log(`Server berjalan pada http://${host}:${port}`);
});
