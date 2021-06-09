import numpy as np
from keras_preprocessing import image
from google.cloud import storage
# import csv

def ml_backend(event, context):
  def upload_file(filename):
    upload_client = storage.Client()
    bucket = upload_client.get_bucket('cap-out')
    write(check_file(filename))

  def check_file(filename):
    download_client = storage.Client()
    bucket = download_client.get_bucket('cap-upload')
    data = bucket.get_blob(filename) 
    img = image.load_img(data, target_size=(150, 150))  # error
    x = image.img_to_array(img)
    x = np.expand_dims(x, axis=0)
    images = np.vstack([x])
    classes = model.predict(images, batch_size=10)
    if(classes.toString() == '[[1. 0. 0. 0. 0. 0.]]'):
      return 'mangga'
    elif(classes.toString() == '[[0. 1. 0. 0. 0. 0.]]'):
      return 'jeruk'
    elif(classes.toString() == '[[0. 0. 1. 0. 0. 0.]]'):
      return 'semangka'
    elif(classes.toString() == '[[0. 0. 0. 1. 0. 0.]]'):
      return 'apel'
    elif(classes.toString() == '[[0. 0. 0. 0. 1. 0.]]'):
      return 'pisang'
    elif(classes.toString() == '[[0. 0. 0. 0. 0. 1.]]'):
      return 'anggur'
    else:
      return 'not found'

  def write(res):
    data = res
    csvfile = 'file.csv'
    # with open(csvfile, 'w', encoding='UTF8') as f:
    #   writer = csv.writer(f)
    #   writer.writerow(data)
    blob = bucket.blob(csvfile)
    blob.upload_from_string(data)

  file = event
  print(f"Processing file: {file['name']}.")
  # print(check_file(file['name']))
  print(upload_file(file['name']))