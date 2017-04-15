
loan_data_clusters = read.csv("C:/Users/Sneha Ravi/Desktop/kmeans_clustering_final.csv")


loan_data_clusters<-loan_data_clusters[-1]
head(loan_data_clusters)

A<-loan_data_clusters[loan_data_clusters$label== 0,] 
B<-loan_data_clusters[loan_data_clusters$label== 1,] 
C<-loan_data_clusters[loan_data_clusters$label== 2,] 
D<-loan_data_clusters[loan_data_clusters$label== 3,] 

nrow(loan_data_clusters)
nrow(A)
nrow(B)
nrow(C)
nrow(D)

train= A [1:566738,]
test= A[566739:809626,] 
nrow(test)
nrow(train)

X_train = train[-13]
y_train = train[13]
X_test = test[-13]
y_test = test[13]

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
