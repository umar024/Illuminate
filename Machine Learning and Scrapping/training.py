# -*- coding: utf-8 -*-

from sklearn.preprocessing import LabelEncoder
import pandas as pd
import numpy as np
from sklearn.cluster import KMeans
from sklearn.neighbors import KNeighborsClassifier

data  = pd.read_csv("D:/FYP/ServerSide/trainingdata.csv")
data2  = pd.read_csv("D:/FYP/ServerSide/permissions.csv")


enc = LabelEncoder()
X = data.iloc[:,1:2]
X = enc.fit_transform(X)
X_test = data2.iloc[:,1:2]
X_test = enc.fit_transform(X_test)
data2.iloc[:,1:2] = X_test
X_test = data2.iloc[:,1:17]
data.iloc[:,1:2] = X
X_train = data.iloc[:,1:17]
y_train = data.iloc[:,17:18]

cols = X_test.columns
for col in cols:
    X_test[col] = X_test[col].astype(float)

neigh = KNeighborsClassifier(n_neighbors=3)
y_train = y_train.to_numpy()
neigh.fit(X_train, y_train)
y_predict = neigh.predict(X_test)
y_predict = pd.DataFrame(y_predict)

df =  pd.read_csv("D:/FYP/ServerSide/permissions.csv")
df["Level"] = y_predict

cat1 = df[df['Level'] == 1]
cat1.to_csv(r'D:/FYP/ServerSide/category1.csv')


cat2 = df[df['Level'] == 2]
cat2.to_csv(r'D:/FYP/ServerSide/category2.csv')

cat3 = df[df['Level'] == 3]
cat3.to_csv(r'D:/FYP/ServerSide/category3.csv')




#kmeans = KMeans(n_clusters = 6).fit(X_train)
#result = kmeans.labels_
