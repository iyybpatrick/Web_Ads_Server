# Web  Ads Server
This is a Ads Server Which displays real Amazon product on the web page.

# Project description

- Designed and implemented web crawler which scraped thousands product data from Amazon using Java, JSoup, Proxy
- Designed and developed Search Ads Web Service which support: Query understanding, Ads selection from inverted index,
  Ads ranking, Ads ﬁlter, Ads pricing, Ads allocation using Java Servlet, Apache Lucene, MySQL-JDBC
- Predicted click probability with features generated from simulated search log (Python, SparkMLlib)
- Implemented Query rewrite with word2vector algorithm (Python, SparkMLlib)

# Implementation Steps

1) The first step is to write crawler(Java code) to crawl real product information from Amazon website, and then saved raw 
   ads data on disk. Product information includes : raw query, ads title(defined as ads keywords in this project),
   ads category, ads price, ads thumbnails, ads price, bid price, ad id(generated continuously)
   
   
   
2) The second step is to read data information line by line, and generate inverted index with memcached. The purpose of inverted index is to find relevant time in shortest time. Key is cleaned query token(clean query with lucene library first), value is Ads list. 

3) Then generate forward index to relate every ad with it's detailed information(title, url, price etc). At the same time, I save information on disk (MySQL database).

4) Query understanding : Select cleaned words from query and title, used sparkMLlib(word2vec algorithm) to train a classifier. Which helped find synonyms for each token word. Then save the synonyms list on disk. While input with online query, use N-gram algorithm to generate sub queries for this certain query, and find out all the relevant ads.

5) Select and generate 8 features from search log and use (Gradient Boosting Decision Tree & Logistic regression) to train pClick model offline. When online, extract feature from input query and use trained model to predict pClick rate.

6) After generating pClick rate, rank ads according to its relevant score and bid price.

7) According to ads' rank score, place ads in front of users.
