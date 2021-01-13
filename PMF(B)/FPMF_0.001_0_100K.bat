javac *.java


java -Xmx2048m Main -lambda 0.001 -rho 0 -gamma 0.8 -d 20 -fnTrainingData ../ML100K/generality/copy1.train -fnTestData ../ML100K/generality/copy1.test -fnOutputData ./testResult/FPMF_0.001_0_100K_copy_1.txt -n 943 -m 1682 -num_iterations 100
java -Xmx2048m Main -lambda 0.001 -rho 0 -gamma 0.8 -d 20 -fnTrainingData ../ML100K/generality/copy2.train -fnTestData ../ML100K/generality/copy2.test -fnOutputData ./testResult/FPMF_0.001_0_100K_copy_2.txt -n 943 -m 1682 -num_iterations 100
java -Xmx2048m Main -lambda 0.001 -rho 0 -gamma 0.8 -d 20 -fnTrainingData ../ML100K/generality/copy3.train -fnTestData ../ML100K/generality/copy3.test -fnOutputData ./testResult/FPMF_0.001_0_100K_copy_3.txt -n 943 -m 1682 -num_iterations 100
java -Xmx2048m Main -lambda 0.001 -rho 0 -gamma 0.8 -d 20 -fnTrainingData ../ML100K/generality/copy4.train -fnTestData ../ML100K/generality/copy4.test -fnOutputData ./testResult/FPMF_0.001_0_100K_copy_4.txt -n 943 -m 1682 -num_iterations 100
java -Xmx2048m Main -lambda 0.001 -rho 0 -gamma 0.8 -d 20 -fnTrainingData ../ML100K/generality/copy5.train -fnTestData ../ML100K/generality/copy5.test -fnOutputData ./testResult/FPMF_0.001_0_100K_copy_5.txt -n 943 -m 1682 -num_iterations 100

pause
