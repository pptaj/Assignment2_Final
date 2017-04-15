setwd("/home/sneha/Documents/ADS /ads_assignment_2")
data <- read.csv("/home/sneha/Documents/ADS /ads_assignment_2/Data/Cleaned/cleaned_loandata.csv", header=TRUE)

###columns to choose: id, loan_amnt, term int_rate, installment, grade , sub_grade, emp_length, inq_last_6mths
#Drop: memeber_id, url, desc,
#change to number - grade, home_ownership, verification_status, issue month, issue_date, loan status, title, application_type,

######adding categorical columns 
#GRADE
data$grade_number=0

data$grade_number[data$grade=="A"]<-1
data$grade_number[data$grade=="B"]<-2
data$grade_number[data$grade=="C"]<-3
data$grade_number[data$grade=="D"]<-4
data$grade_number[data$grade=="E"]<-5
data$grade_number[data$grade=="F"]<-6
data$grade_number[data$grade=="G"]<-7
summary(data$grade_number)

#Home_Ownership
summary(data$home_ownership)
data$home_ownership_number[data$home_ownership=="MORTGAGE"]<-1
data$home_ownership_number[data$home_ownership=="OWN"]<-2
data$home_ownership_number[data$home_ownership=="RENT"]<-3
data$home_ownership_number[data$home_ownership=="UNKNOWN"]<-9
summary(data$home_ownership_number)
head(data$home_ownership_number)

#verification_status
summary(data$verification_status)
data$verification_status_nmbr[data$verification_status =="Not Verified"]<-1
data$verification_status_nmbr[data$verification_status=="Source Verified"]<-2
data$verification_status_nmbr[data$verification_status=="Verified"]<-3
head(data$verification_status_nmbr)
#Issue_loan_flag
data$issue_loan_flag = 0
data$issue_loan_flag[data$loan_status =="Current"]<-1
data$issue_loan_flag[data$loan_status=="Fully Paid"]<-1
data$issue_loan_flag[data$loan_status=="Late (31-120 days)"]<-1
data$issue_loan_flag[data$loan_status=="Late (16-30 days)"]<-1
data$issue_loan_flag[data$loan_status=="In Grace Period"]<-1
data$issue_loan_flag[data$loan_status=="Other"]<-1


#purpose
data$purpose_nmbr[data$purpose =="car"]<-1
data$purpose_nmbr[data$purpose =="credit_card"]<-2
data$purpose_nmbr[data$purpose =="debt_consolidation"]<-3
data$purpose_nmbr[data$purpose =="educational"]<-4
data$purpose_nmbr[data$purpose =="home_improvement"]<-5
data$purpose_nmbr[data$purpose =="house"]<-6
data$purpose_nmbr[data$purpose =="major_purchase"]<-7
data$purpose_nmbr[data$purpose =="medical"]<-8
data$purpose_nmbr[data$purpose =="moving"]<-9
data$purpose_nmbr[data$purpose =="other"]<-10
data$purpose_nmbr[data$purpose =="renewable_energy"]<-11
data$purpose_nmbr[data$purpose =="small_business"]<-12
data$purpose_nmbr[data$purpose =="vacation"]<-13
data$purpose_nmbr[data$purpose =="wedding"]<-14
#application type
data$applcn_type_nbr[data$application_type =="DIRECT_PAY"]<-1
data$applcn_type_nbr[data$application_type =="INDIVIDUAL"]<-2
data$applcn_type_nbr[data$application_type =="JOINT"]<-3

write.csv(data, file = "/home/sneha/Documents/ADS /ads_assignment_2/Data/Cleaned/cleaned_loandata_numeric.csv")

summary(data)

str(data)



#read required columns
featuresFromLoanData = c('loan_amnt', 'term', 'installment', 'emp_length', 
                          'delinq_2yrs', 'inq_last_6mths', 'mths_since_last_delinq',
                          'mths_since_last_record', 'open_acc', 'pub_rec', 'revol_bal', 
                          'total_acc', 'annual_inc', 'dti', 'acc_now_delinq',
                          'tot_cur_bal', 'total_rev_hi_lim', 'acc_open_past_24mths', 'avg_cur_bal',
                          'chargeoff_within_12_mths', 'delinq_amnt', 'mo_sin_old_il_acct',
                          'mo_sin_old_rev_tl_op', 'mo_sin_rcnt_rev_tl_op', 'mo_sin_rcnt_tl',
                          'mort_acc', 'mths_since_recent_bc', 'mths_since_recent_bc_dlq',
                          'mths_since_recent_inq', 'mths_since_recent_revol_delinq', 
                          'num_accts_ever_120_pd', 'num_actv_bc_tl', 'num_actv_rev_tl',
                          'num_bc_sats', 'num_bc_tl', 'num_il_tl', 'num_op_rev_tl',
                          'risk_score', 'avg_fico_range', 'grade_number','home_ownership_number','verification_status_nmbr',
                         'purpose_nmbr','applcn_type_nbr')

data_clustering <-  subset(data, select=c('loan_amnt', 'term', 'installment', 'emp_length', 
                                         'delinq_2yrs', 'inq_last_6mths', 'mths_since_last_delinq',
                                         'mths_since_last_record', 'open_acc', 'pub_rec', 'revol_bal', 
                                         'total_acc', 'annual_inc', 'dti', 'acc_now_delinq',
                                         'tot_cur_bal', 'total_rev_hi_lim', 'acc_open_past_24mths', 'avg_cur_bal',
                                         'chargeoff_within_12_mths', 'delinq_amnt', 'mo_sin_old_il_acct',
                                         'mo_sin_old_rev_tl_op', 'mo_sin_rcnt_rev_tl_op', 'mo_sin_rcnt_tl',
                                         'mort_acc', 'mths_since_recent_bc', 'mths_since_recent_bc_dlq',
                                         'mths_since_recent_inq', 'mths_since_recent_revol_delinq', 
                                         'num_accts_ever_120_pd', 'num_actv_bc_tl', 'num_actv_rev_tl',
                                         'num_bc_sats', 'num_bc_tl', 'num_il_tl', 'num_op_rev_tl',
                                         'risk_score', 'avg_fico_range', 'grade_number','home_ownership_number','verification_status_nmbr',
                                         'purpose_nmbr','applcn_type_nbr'))

#Test 2
 dtest1 <- subset(data, select = c('loan_amnt', 'term', 'installment', 'emp_length', 
                                   'delinq_2yrs', 'inq_last_6mths', 'mths_since_last_delinq',
                                   'mths_since_last_record', 'open_acc', 'pub_rec', 'revol_bal', 
                                   'total_acc', 'annual_inc', 'dti', 'acc_now_delinq',
                                   'tot_cur_bal', 'total_rev_hi_lim', 'risk_score', 'avg_fico_range', 'grade_number','home_ownership_number','verification_status_nmbr',
                                   'purpose_nmbr','applcn_type_nbr'))

install.packages("fastcluster")
library('fastcluster')
install.packages("C50")
require(C50)
?kmeans
km.out <- kmeans(data_clustering,6,nstart=10)
km.out
km.out$size
km.out$cluster
plot(iris, col=(km.out$cluster), main="K-mean result with k=3") #Scatterplot matrix


install.packages("h2o")
library(h2o)
h2o.init()
d1path <- system.file("h2odata", "/home/sneha/Documents/ADS /ads_assignment_2/Data/Cleaned/cleaned_loandata_numeric.csv", package = "h2o")
d1.hex <- h20.uploadFile(path = d1path )
h20.kmeans(traininf_frame = d1.hex, k=10, x=c('loan_amnt', 'term', 'installment', 'emp_length', 
                                               'delinq_2yrs', 'inq_last_6mths', 'mths_since_last_delinq',
                                               'mths_since_last_record', 'open_acc', 'pub_rec', 'revol_bal', 
                                               'total_acc', 'annual_inc', 'dti', 'acc_now_delinq',
                                               'tot_cur_bal', 'total_rev_hi_lim', 'risk_score', 'avg_fico_range', 'grade_number','home_ownership_number','verification_status_nmbr',
                                               'purpose_nmbr','applcn_type_nbr'))