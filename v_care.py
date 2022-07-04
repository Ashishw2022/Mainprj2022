# -*- coding: utf-8 -*-
"""V_care.ipynb

Automatically generated by Colaboratory.

Original file is located at
    https://colab.research.google.com/drive/1k74HjLtLTeZDFSA7c7wHwgSOr0Aw3K6C
"""

# Commented out IPython magic to ensure Python compatibility.
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
# %matplotlib inline

Train_set = pd.read_csv('/content/Training.csv')

Train_set.shape

Train_set.info()

Train_set.head()

dict_Diseases = {'Fungal infection':0, 'Allergy':1, 'GERD':2, 'Chronic cholestasis':3,
       'Drug Reaction':4, 'Peptic ulcer diseae':5, 'AIDS':6, 'Diabetes ':7,
       'Gastroenteritis':8, 'Bronchial Asthma':9, 'Hypertension ':10, 'Migraine':11,
       'Cervical spondylosis':12, 'Paralysis (brain hemorrhage)':13, 'Jaundice':14,
       'Malaria':15, 'Chicken pox':16, 'Dengue':17, 'Typhoid':18, 'hepatitis A':19,
       'Hepatitis B':20, 'Hepatitis C':21, 'Hepatitis D':22, 'Hepatitis E':23,
       'Alcoholic hepatitis':24, 'Tuberculosis':25, 'Common Cold':26, 'Pneumonia':27,
       'Dimorphic hemmorhoids(piles)':28, 'Heart attack':29, 'Varicose veins':30,
       'Hypothyroidism':31, 'Hyperthyroidism':32, 'Hypoglycemia':33,
       'Osteoarthristis':34, 'Arthritis':35,
       '(vertigo) Paroymsal  Positional Vertigo':36, 'Acne':37,
       'Urinary tract infection':38, 'Psoriasis':39, 'Impetigo':40}

list_Diseases = Train_set['prognosis'].unique()

#Let's map Train_set['prognosis'] to dict_Diseases

Train_set['prognosis'] = Train_set['prognosis'].map(dict_Diseases)

#Let's assign Prognosis to Y(label)

Y = Train_set['prognosis']

#Let's assign the columns other than prognosis to Features

Features = Train_set.drop(['prognosis'],axis=1)

Features.head()

"""#Train_Test_Split"""

from sklearn.model_selection import train_test_split

#Let's assign that PCA_Norm_Features to X so that we could use this X to train our model
X=Features
#X = Norm_Features

#To measure the training accuracy and test accuracy while training the model, we split the given Train_set
no_diseases = 41
targets = Y
one_hot = np.eye(41)[targets]

X_train , X_test , y_train , y_test = train_test_split( X , one_hot , test_size=0.33, random_state = 6)
x_train , x_test , Y_train , Y_test = train_test_split( X , Y , test_size=0.33, random_state = 6)

"""#Keras"""

import keras
from keras.models import Sequential
from keras.layers import Dense
import tensorflow as tf
# Neural network
model = Sequential()
model.add(Dense(50, input_dim= 132, activation="relu"))
model.add(Dense(30, activation="relu"))
model.add(Dense(20, activation="relu"))
model.add(Dense(41, activation="softmax"))
model.compile(loss='categorical_crossentropy', optimizer='adam', metrics=['accuracy'])
history = model.fit(X_train, y_train, epochs=100, batch_size=64)

"""#Sample"""

y_pred = model.predict(X_test)
#Converting predictions to label
pred = list()
for i in range(len(y_pred)):
    pred.append(np.argmax(y_pred[i]))
#Converting one hot encoded test label to label
test = list()
for i in range(len(y_test)):
    test.append(np.argmax(y_test[i]))
count = 0
for t in range(len(test)):
  if test[t] == pred[t]:
    count += 1

"""#Tflite"""

from tensorflow import lite

# Convert Keras model to TF Lite format.
converter = tf.lite.TFLiteConverter.from_keras_model(model)
tflite_float_model = converter.convert()

# Show model size in KBs.
float_model_size = len(tflite_float_model) / 1024
print('Float model size = %dKBs.' % float_model_size)

# Commented out IPython magic to ensure Python compatibility.
# Re-convert the model to TF Lite using quantization.
converter.optimizations = [tf.lite.Optimize.DEFAULT]
tflite_quantized_model = converter.convert()

# Show model size in KBs.
quantized_model_size = len(tflite_quantized_model) / 1024
print('Quantized model size = %dKBs,' % quantized_model_size)
print('which is about %d%% of the float model size.'\
#       % (quantized_model_size * 100 / float_model_size))

# Save the quantized model to file to the Downloads directory
f = open('vcare.tflite', "wb")
f.write(tflite_quantized_model)
f.close()

# Download the digit classification model
from google.colab import files
files.download('vcare.tflite')

print('`vcare.tflite` has been downloaded')