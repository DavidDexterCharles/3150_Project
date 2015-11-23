//Student ID: 811000385
//Assignment 1



Usage

0. First Compile All the Classes: "QuestionHandler .java", "GameServerTcp .java" , "GameClientTCP.java"


1. First Run GameServerTCP

	Details: GameServerTcp is the TCP server and uses the QuestionHandler class to get questions to ask to the client and to validate clients response. The server does not stop running when the client
	ends the game however, when the client restart their application i.e.(GameClientTCP.java) the server goes to its original state i.e. it resets everything  so that the client would start a new game.

	
	The QuestionHandler class have methods that are used in GameServerTCP.java, these are listed below:
	
	First Create a QuestionHandler object:

	private QuestionHandler question = new QuestionHandler();

	The methods used:

	>question.ask()// This function call returns  a question from the list of questions
	>question.checkAnswer("Some response from client ");// This function call returns a string stating whether the respose given was correct or incorrect
	>question.isAvailable()//This function call returns a boolean value of true if the question is available and false otherwise, a question is available if the queue is not empty or all the questions have not been asked already.
	>question.getPoints()// This function call returns the number of points that would have been gained so far for each correct answer to each question asked.
	

2. Then Run GameClientTCP
	
	Type Stop to exit the game otherwise the Game ends after all questions have been asked includeing the ones on the queue from the server.

	
	Details: When GameClientTCP.java is run the client/user receives the first question without having to enter input into the console, 
	this is because the code does this on the users behalf and the server responds with a new question. Each time the user gives a response to a question
	the server intern respond with a verification status of whether the answer giving was correct or incorrect along with the correct answer if the answer the user gave was incorrect.
	Along with the verification status, the server also sends the new question to the client to be asked.

	if the user types stop into the console the game would end and the score the user received so far would be displayed, however if the user completes the game then their final score would be displayed in the console.
