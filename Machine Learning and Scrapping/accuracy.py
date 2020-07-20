# -*- coding: utf-8 -*-

from sklearn.preprocessing import LabelEncoder
import pandas as pd
import numpy as np
from sklearn.cluster import KMeans
from sklearn.neighbors import KNeighborsClassifier
from sklearn.metrics import accuracy_score, confusion_matrix


data  = pd.read_csv("D:/FYP/ServerSide/trainingdata.csv")
data = data.sample(frac=1).reset_index(drop=True)


enc = LabelEncoder()
X = data.iloc[:,1:2]
X = enc.fit_transform(X)

data.iloc[:,1:2] = X
X_train = data.iloc[0:45,1:17]
y_train = data.iloc[0:45:,17:18]
X_test = data.iloc[45:90, 1:17]
y_actual = data.iloc[45:90, 17:18]

cols = X_test.columns
for col in cols:
    X_test[col] = X_test[col].astype(float)

neigh = KNeighborsClassifier(n_neighbors=3)
y_train = y_train.to_numpy()
neigh.fit(X_train, y_train)
y_predict = neigh.predict(X_test)
y_predict = pd.DataFrame(y_predict)



print(accuracy_score(y_actual, y_predict))


kmeans = KMeans(n_clusters = 3).fit(X_train,y_train)
result = kmeans.labels_

y_pred2 = kmeans.predict(X_test)
print(accuracy_score(y_actual, y_pred2))

