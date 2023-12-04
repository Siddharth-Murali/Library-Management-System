make:
	javac -cp .:activation-1.1.1.jar:javax.mail-1.6.2.jar Main.java
run:
	make
	java -cp .:activation-1.1.1.jar:javax.mail-1.6.2.jar Main
clean:
	rm *.class
	rm *.ser
	
