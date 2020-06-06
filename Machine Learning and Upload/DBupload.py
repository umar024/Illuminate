import requests
import pandas as pd
from google_play_scraper import app


def upload(x):
    url1 = "https://nasfistsolutions.com/illuminate/InsertData.php?"
    request="request="+str(x)
    url = url1+request
    col_list = ["Package"]   # to select a single column i.e. "Package" from csv file
    
    if(x==1):                #to select different csv according to its category in each iteration
        data = pd.read_csv("D:\FYP\ServerSide\category1.csv", usecols=col_list)
    elif (x==2):
        data = pd.read_csv("D:\FYP\ServerSide\category2.csv", usecols=col_list)
    else:
        data = pd.read_csv("D:\FYP\ServerSide\category3.csv", usecols=col_list)

    for y in data["Package"]:
        try: 
            result = app(       #fetching app data from playstore using google_play_scraper
            y,
            lang='en',          # defaults to 'en',
            country='us'        # defaults to 'us'
            )
            payload = {         # creating payload to send to server
                'title':result['title'],
                'description':result['description'],
                'installs':result['installs'],
                'score':result['score'],
                'ratings':result['ratings'],
                'reviews':result['reviews'],
                'price':result['price'],
                'genreId':result['genreId'],
                'icon':result['icon'],
                'size':result['size'],
                'url':result['url'],
                'released':result['released'],
                'version':result['version'],
                'summary':result['summary']
            }
            response = requests.post(url, payload)  #sending data to server
            print(response.text)
        except:
            print("not found")
        
    
x=0
for z in range(3):
    x=x+1
    upload(x)
    

