# Project 3
Irene Liu  
irliu@chapman.edu  
2313260  
CPSC 380  


*To run: compile and run Driver.java with an int argument corresponding to the number of times you want Thread 1 to DoWork()*  
*example: java Driver 100*

## Design:
The Scheduler assigns priorities to 4 threads during initialization. It finds the shortest thread and assigns it the next highest priority but below the scheduler itself. At each "clock tick" of 10ms, the Scheduler wakes up from a thread.sleep and determines whether or not a thread should be run. If the scheduled thread has not finished it's previously scheduled task, then an overrun occured and the thread will not be scheduled to run for that period.  
  
The 4 threads share 1 semaphore, so assuming that the priorities are set and work as expected, thread 1 with the shortest period (and highest priority) should block all lower priority threads whenever it is scheduled. However, I did not implement any sort of protection against priority inversion, so we can still expect cases where higher priority threads are blocked by the lower priority ones if we were to change Threads 2, 3, and 4 to run doWork() more times.
