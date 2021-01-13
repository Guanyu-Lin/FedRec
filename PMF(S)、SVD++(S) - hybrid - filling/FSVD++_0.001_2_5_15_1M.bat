javac *.java


java -Xmx10240m Main -lambda 0.001 -rho 2 -delta_o 1 -gamma 0.01 -d 20 -fnTrainingData ../ML1M/generality/copy1.train -fnTestData ../ML1M/generality/copy1.test -fnOutputData ./testResult/FSVD_0.001_2_5_15_1M_copy_1.txt -n 6040 -m 3952 -num_iterations 100 -start_hybrid_averaging_iterations 5 -local_train_iterations 15
java -Xmx10240m Main -lambda 0.001 -rho 2 -delta_o 1 -gamma 0.01 -d 20 -fnTrainingData ../ML1M/generality/copy2.train -fnTestData ../ML1M/generality/copy2.test -fnOutputData ./testResult/FSVD_0.001_2_5_15_1M_copy_2.txt -n 6040 -m 3952 -num_iterations 100 -start_hybrid_averaging_iterations 5 -local_train_iterations 15
java -Xmx10240m Main -lambda 0.001 -rho 2 -delta_o 1 -gamma 0.01 -d 20 -fnTrainingData ../ML1M/generality/copy3.train -fnTestData ../ML1M/generality/copy3.test -fnOutputData ./testResult/FSVD_0.001_2_5_15_1M_copy_3.txt -n 6040 -m 3952 -num_iterations 100 -start_hybrid_averaging_iterations 5 -local_train_iterations 15
java -Xmx10240m Main -lambda 0.001 -rho 2 -delta_o 1 -gamma 0.01 -d 20 -fnTrainingData ../ML1M/generality/copy4.train -fnTestData ../ML1M/generality/copy4.test -fnOutputData ./testResult/FSVD_0.001_2_5_15_1M_copy_4.txt -n 6040 -m 3952 -num_iterations 100 -start_hybrid_averaging_iterations 5 -local_train_iterations 15
java -Xmx10240m Main -lambda 0.001 -rho 2 -delta_o 1 -gamma 0.01 -d 20 -fnTrainingData ../ML1M/generality/copy5.train -fnTestData ../ML1M/generality/copy5.test -fnOutputData ./testResult/FSVD_0.001_2_5_15_1M_copy_5.txt -n 6040 -m 3952 -num_iterations 100 -start_hybrid_averaging_iterations 5 -local_train_iterations 15

pause
