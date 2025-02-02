# Movie Recommendation System

The movie recommendation system has been programmed in python due to the robust tool set for statistical analysis.The java program  is running a system call to execute the python file on either a mac or windows pc by calling the program using the operating system’s CLI.
To get this program to run, install python 3 and run the following command to install the proper dependancies: pip3 install scikit-learn np pandas scipy mysql-connector-python
From here comment out line 14 of resources/user/profile.html. If the user is signed in, on their own profile, they are able
This program is taking in information from the moviedock mysql database, specifically user reviews and ratings and outputting a csv file with a list of movie recommendations. The model has been created using the movielens dataset from kaggle.
Movie_rec.py is using a machine learning technique here known as item-item collaborative filtering, that takes in a dataset of users and the items they have reviewed and returns a model of item similarity.
The model is created by taking the user/item matrix containing the user/movie reviews of each movie in the data set and creating a model that clusters together similar movies. From here the distance can be found between movies, showing similarities.
From here movies that have been rated highly or favorited by a user can output recommended movies to a file which can then be displayed to the user.
