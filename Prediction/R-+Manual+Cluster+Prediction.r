
loan_data = read.csv("C:/Users/Sneha Ravi/Desktop/manual_cluster.csv")

loan_data_clusters = read.csv("C:/Users/Sneha Ravi/Desktop/kmeans_clustering_final.csv")

x<- loan_data[-1]
y<- loan_data_clusters[-1]

colnames( x )

A<-merge(x, y)


head(A)
nrow(A)
write.csv(A,"C:/Users/Sneha Ravi/Desktop/merged_clustering_final.csv")

X<-A[-17]
y<-A[17]
head(y)

#Split into Train and Test (870%train, 30%test)
#install.packages("caTools", repos='http://cran.us.r-project.org')
#library(caTools)
train= A [1:925203,]
test= A[925204:1321847,] 
X_train = train[-17]
y_train = train[17]
X_test = test[-17]
y_test = test[17]

head(test$int_rate)

head(test$int_rate)

#Linear Regression
#install.packages("forecast", repos='http://cran.us.r-project.org')
library(forecast)
lm.fit = lm(int_rate ~ .,data = train)
summary(lm.fit)


#Linear Regression
library(forecast)
pred = predict(lm.fit, test)
accuracy(pred, train$int_rate)
head(pred)
pred_interest <- as.data.frame(pred)
test_interest <- as.data.frame(test$int_rate)
nrow(pred_interest)
nrow(test_interest)
#VALIDATION
x <- (test_interest - pred_interest)
MSE <- sum((x^2))/nrow(test_interest)
RMSE<-sqrt(MSE)
print(RMSE)



library(randomForest)
library(MASS)
randomForestfit <- randomForest(int_rate ~ .,data = train, n_tree=1)
