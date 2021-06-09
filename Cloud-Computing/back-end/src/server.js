const http = require('http');
const Firestore = require('@google-cloud/firestore');
const {Storage} = require('@google-cloud/storage');
const fs = require('fs');
const { nanoid } = require('nanoid');


const db = new Firestore({
	projectId: 'capstone-praject-b21-cap0293',
	keyFilename: './capstone-praject-b21-cap0293-b706e79739bb.json',
});

const storage = new Storage({
	projectId: 'capstone-praject-b21-cap0293',
	keyFilename: './capstone-praject-b21-cap0293-3bce7aa62eb4.json'
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
    } else if (url === '/scan' || url === '/scan/') {
        if(method === 'POST') {
			let body = [];

			request.on('data', (chunk) => {
				body.push(chunk);
				body = Buffer.concat(body).toString();
			});

			request.on('end', () => {
				const image_req = JSON.parse(body);
				console.log(image_req.file);

				const bucketName = 'cap-upload';
				const filePath = './upload/' + image_req.file;
				const destFileName = nanoid(8) + '_' + image_req.file;

				const bucketName2 = 'cap-out';
				const fileName2 = 'ml-result.csv';
				const destFileName2 = './download/file.csv';

				async function uploadFile() {
					await storage.bucket(bucketName).upload(filePath, {
						destination: destFileName,
					});
					console.log(`${filePath} uploaded to ${bucketName}`);
				}
				async function getFile() {
				    var time = 10, the_interval = time * 1000;
				    let isFileUpdate = false;
				    const options = {
				    	destination: destFileName2,
				    };
					setTimeout(function() {
						storage.bucket(bucketName2).file(fileName2).download(options);
				    	console.log(`gs://${bucketName2}/${fileName2} downloaded to ${destFileName2}`);

				    	setTimeout(function() {
				    		// body...
							var fruit = fs.readFileSync('./download/file.csv')
							    .toString() 
							    .split('\n') 
							    .map(e => e.trim())
							    .map(e => e.split(';').map(e => e.trim())); 
							// console.log(fruit[0][0]);
							// response.end(JSON.stringify(fruit));
							db.collection('info_buah').get().then(function(querySnapshot) {
								var data = "";
								querySnapshot.forEach(function(doc) {
									var test = doc.data()['name'];
									if (doc.data()['name'].toLowerCase() === fruit[0][0].toLowerCase()) {
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
				    	}, 3000)
					}, the_interval);
				}

				uploadFile().catch(console.error);
				getFile().catch(console.error);

				// while(!isFileUpdate){
				// }
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
