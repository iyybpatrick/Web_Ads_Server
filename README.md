# Web  Ads Server
This is a Ads Server Which displays real Amazon product on the web page.

# Project description

- Designed and implemented web crawler which scraped thousands product data from Amazon using Java, JSoup, Proxy
- Designed and developed Search Ads Web Service which support: Query understanding, Ads selection from inverted index,
  Ads ranking, Ads ﬁlter, Ads pricing, Ads allocation using Java Servlet, Apache Lucene, MySQL-JDBC
- Predicted click probability with features generated from simulated search log (Python, SparkMLlib)
- Implemented Query rewrite with word2vector algorithm (Python, SparkMLlib)

# Implementation Steps

1) The first step is to use crawler to crawl real product information from Amazon website, and then general raw 
   ads data file for next steps. Product information includes : raw query, ads title(defined as ads keywords in this project),
   ads category, ads price, ads thumbnails, ads price, bid price, ad id(generated continuously)
   
   
   
2) The second step is to 
