# -*- coding: utf-8 -*-
"""
Created on Tue Dec  4 09:42:13 2018

@author: fabian
"""

import numpy as np 
import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns

path="C:/Users/PC/Downloads/Housing/train.csv"
path2="'C:/Python_Programs/Housing/train.csv'"
data_df=pd.read_csv(path)#importing data


"""To Check how many numeric and categorical data are present in the data"""

numeric=data_df.dtypes[data_df.dtypes!='object'].index #Checking for numeric column

print(len(numeric))

categorical=data_df.dtypes[data_df.dtypes=='object'].index #Checking for categorical data

print(len(categorical))

""" ---------------------------------------------------------------------------- """

""" Checking the distribution of SalePrice(target price) """

sns.distplot(data_df.SalePrice)

""" -------------------------------------------------------------------- """

""" Correlation matrix """


cormat=data_df.corr()
plt.subplots(figsize=(12,9))
sns.heatmap(cormat)



k=10
cols = cormat.nlargest(k, 'SalePrice')['SalePrice'].index
cm = np.corrcoef(data_df[cols].values.T)
hm = sns.heatmap(cm, cbar=True, annot=True, square=True, fmt='.2f', annot_kws={'size': 10}, yticklabels=cols.values, xticklabels=cols.values)
plt.show()


cols = ['SalePrice', 'OverallQual', 'GrLivArea', 'GarageCars', 'TotalBsmtSF', 'FullBath', 'YearBuilt']
sns.pairplot(data_df[cols], size = 2.5)
plt.show();



var='GrLivArea'
data=pd.concat([data_df['SalePrice'],data_df[var]],axis=1)
data.plot.scatter(x=var, y='SalePrice', ylim=(0,800000))

var='PoolArea'
data=pd.concat([data_df['SalePrice'],data_df[var]],axis=1)
data.plot.scatter(x=var, y='SalePrice', ylim=(0,800000))

#PoolArea cannot Predict SalePrice
var='LotShape'
data=pd.concat([data_df['SalePrice'],data_df[var]],axis=1)
data.plot.scatter(x=var, y='SalePrice', ylim=(0,800000))


corrmat = data_df.corr()
f, ax = plt.subplots(figsize=(30, 30))
hm = sns.heatmap(corrmat, cbar=False, annot=True, square=True, fmt='.2f', annot_kws={'size': 10})


k = 10 #number of variables for heatmap
cols = corrmat.nlargest(k, 'SalePrice')['SalePrice'].index
cm = np.corrcoef(data_df[cols].values.T)
sns.set(font_scale=1.25)
hm = sns.heatmap(cm, cbar=True, annot=True, square=True, fmt='.2f', annot_kws={'size': 10}, yticklabels=cols.values, xticklabels=cols.values)
plt.show()


#missing Data


misData=pd.DataFrame()


col=data_df.columns
mis=round(100*(data_df.isnull().sum()/len(data_df.index)),2)
total=data_df.isnull().sum()
misData=pd.concat([total,mis],axis=1,keys=['Total','Percent'])







""" Checking for missing values """

missing=data_df.isnull().sum()
missing=missing[missing>0]
missing.sort_values(inplace=True)
missing.plot.bar()

#checking the percentage of missing data


total=len(data_df.index)

miss=data_df.isnull().sum()

per=(miss*100)/total

per=per[per>0]
per.sort_values(inplace=True)
print(per)

#19 attributes have missing values, 5 over 50% of all data. Most of times NA means lack of subject described by attribute, like missing pool, fence, no garage and basement.






""" SalePrice does not follow normal distribution """

import scipy.stats as st

y=data_df.SalePrice
plt.figure(1)
plt.title('Johnson SU')
sns.distplot(y,kde=False,fit=st.johnsonsu)

plt.figure(2)
plt.title('Normal')
sns.distplot(y,kde=False,fit=st.moyal)







data=pd.DataFrame()
for x in range(len(numeric)):
 var=numeric[x]
 data=pd.concat([data,data_df[var]],axis=1)
 
 
data_df.drop('Id', axis=1, inplace=True)
data_df.drop('SalePrice', axis=1, inplace=True)

numeric1 = [f for f in data_df.columns if data_df.dtypes[f] != 'object']
numeric1=numeric1.remove('Id')
numeric1=numeric1.remove('SalePrice')




def boxplot(x, y, **kwargs):
    plt.scatter(x=x, y=y)

f = pd.melt(data_df,id_vars=['SalePrice'],  value_vars=numeric1)
g = sns.FacetGrid(f, col="variable",  col_wrap=2, sharex=False, sharey=False)
g = g.map(boxplot, "value", "SalePrice")





"""LotFrontage"""

plt.scatter(x='LotFrontage',y='SalePrice',data=data_df)
plt.xlabel('LotFrontage')
plt.ylabel('SalePrice')




"""LotArea"""

plt.scatter(x='LotArea',y='SalePrice',data=data_df)
plt.xlabel('LotArea')
plt.ylabel('SalePrice')






"""MasVnrArea"""



plt.scatter(x='MasVnrArea',y='SalePrice',data=data_df)
plt.xlabel('MasVnrArea')
plt.ylabel('SalePrice')





"""BsmtFinSF1"""


plt.scatter(x='BsmtFinSF1',y='SalePrice',data=data_df)
plt.xlabel('BsmtFinSF1')
plt.ylabel('SalePrice')


"""BsmtFinSF2"""

plt.scatter(x='BsmtFinSF2',y='SalePrice',data=data_df)
plt.xlabel('BsmtFinSF2')
plt.ylabel('SalePrice')





numeric1=['LotFrontage','LotArea','MasVnrArea','BsmtFinSF1','BsmtFinSF2','BsmtUnfSF','TotalBsmtSF','1stFlrSF','2ndFlrSF','LowQualFinSF','GrLivArea','BsmtFullBath','BsmtHalfBath','FullBath','HalfBath','GarageCars','GarageArea','WoodDeckSF','OpenPorchSF','EnclosedPorch','3SsnPorch','ScreenPorch','PoolArea']
                                                                                                                    
                                                                                     




"""MSSubClass"""

sns.boxplot(x="MSSubClass", y="SalePrice",data=data_df)


data_df.Alley=data_df.Alley.map({'Pave':1, 'Grvl':2})
data_df.loc[np.isnan(data_df.Alley),['Alley']]= 3


"""MSZoning"""
""" RL:1 RM:2 C(all):3 FV:4 RH:5 """

data_df.MSZoning=data_df.MSZoning.map({'RL':1,'RM':2,'C(all)':3,'FV':4,'RH':5})


""" LandContour """
"""Lvl:1 Bnk:2 Low=3 HLS:4"""

data_df.LandContour=data_df.LandContour.map({'Lvl':1,'Bnk':2,'Low':3,'HLS':4})



""" Utilities """
""" AllPub:1 NoSeWa:2 """

data_df.Utilities=data_df.Utilities.map({'AllPub':1,'NoSeWa':2})



""" LotConfig """
""" Inside:1 Corner:2 CulDSac:3 FR2:4 FR3:5 """

data_df.LotConfig=data_df.LotConfig.map({'Inside':1,'Corner':2,'CulDSac':3,'FR2':4,'FR3':5})


""" LandSlope """
""" Gtl:1 Mod:2 Sev:3 """

data_df.LandSlope=data_df.LandSlope.map({'Gtl':1,'Mod':2,'Sev':3})





























