javac *.java


java -Xmx10240m Main -lambda 0.001 -rho 0 -gamma 0.8 -d 20 -fnTrainingData ../ML1M/generality/copy4.train -fnTestData ../ML1M/generality/copy4.test -fnOutputData ./testResult/FPMF_0.001_0_1M_copy_4.txt -n 6040 -m 3952 -num_iterations 100
java -Xmx10240m Main -lambda 0.001 -rho 0 -gamma 0.8 -d 20 -fnTrainingData ../ML1M/generality/copy5.train -fnTestData ../ML1M/generality/copy5.test -fnOutputData ./testResult/FPMF_0.001_0_1M_copy_5.txt -n 6040 -m 3952 -num_iterations 100

pause
