javac *.java

java -Xmx4096m Main -lambda 0.001 -rho 3 -delta_o 0 -gamma 0.01 -d 20 -fnTrainingData ../ML1M/generality/copy1.train -fnTestData ../ML1M/generality/copy1.test -fnOutputData ./testResult/FPMF_0.001_3_1M_copy_1.txt -n 6040 -m 3952 -num_iterations 100
java -Xmx4096m Main -lambda 0.001 -rho 3 -delta_o 0 -gamma 0.01 -d 20 -fnTrainingData ../ML1M/generality/copy2.train -fnTestData ../ML1M/generality/copy2.test -fnOutputData ./testResult/FPMF_0.001_3_1M_copy_2.txt -n 6040 -m 3952 -num_iterations 100
java -Xmx4096m Main -lambda 0.001 -rho 3 -delta_o 0 -gamma 0.01 -d 20 -fnTrainingData ../ML1M/generality/copy3.train -fnTestData ../ML1M/generality/copy3.test -fnOutputData ./testResult/FPMF_0.001_3_1M_copy_3.txt -n 6040 -m 3952 -num_iterations 100
java -Xmx4096m Main -lambda 0.001 -rho 3 -delta_o 0 -gamma 0.01 -d 20 -fnTrainingData ../ML1M/generality/copy4.train -fnTestData ../ML1M/generality/copy4.test -fnOutputData ./testResult/FPMF_0.001_3_1M_copy_4.txt -n 6040 -m 3952 -num_iterations 100
java -Xmx4096m Main -lambda 0.001 -rho 3 -delta_o 0 -gamma 0.01 -d 20 -fnTrainingData ../ML1M/generality/copy5.train -fnTestData ../ML1M/generality/copy5.test -fnOutputData ./testResult/FPMF_0.001_3_1M_copy_5.txt -n 6040 -m 3952 -num_iterations 100

pause
