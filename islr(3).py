# -*- coding: utf-8 -*-
"""
Created on Mon Jan 28 10:57:08 2019

@author: Admin
"""

import numpy as np
import pandas as pd
from sklearn.model_selection import train_test_split
import matplotlib.pyplot as plt
import seaborn as sns
from sklearn.metrics import r2_score
from sklearn import linear_model
import statsmodels.formula.api as smf

data_df=pd.read_csv("C:/Users/Admin/Downloads/Housing/train.csv")

d=data_df.GrLivArea
d=pd.concat([data_df.GrLivArea,data_df.GarageArea],axis=1)


""" GrLiveArea """

X=data_df.GrLivArea.values
#X=d.values
X=X.reshape(-1,1)
Y=data_df.SalePrice.values
y=Y.reshape(-1,1)

X_train,X_test,y_train,y_test = train_test_split(X,y,test_size=0.3,random_state=28)


sns.regplot(data_df.GrLivArea,data_df.SalePrice)

linreg = linear_model.LinearRegression()   #instantiate linear regression

linreg.fit(X_train,y_train)

y_pred=linreg.predict(X_test)

R2=r2_score(y_test,y_pred)



""" GarageArea """


X=data_df.GarageArea.values
#X=d.values
X=X.reshape(-1,1)
Y=data_df.SalePrice.values
y=Y.reshape(-1,1)

X_train,X_test,y_train,y_test = train_test_split(X,y,test_size=0.3,random_state=28)


sns.regplot(data_df.GarageArea,data_df.SalePrice)

linreg = linear_model.LinearRegression()   #instantiate linear regression

linreg.fit(X_train,y_train)

y_pred=linreg.predict(X_test)

R2=r2_score(y_test,y_pred)


""" P value """

est = smf.ols('SalePrice ~ MoSold', data_df).fit()

est.summary().tables[1]


est = smf.ols('SalePrice ~ ScreenPorch + GrLivArea + GarageArea', data_df).fit()

est.summary()





