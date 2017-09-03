Within you will find /src, /bin, /resources, /report, /makefile and an ExperimentBash Script

Instructions for makefile:
The command "make Main" will run both parallel and sequential algorithms and print the results \
in two separate file under resources with the filterSize and arraySize appended to the name.
You can choose the name of the datafile, the filtersize and the arraysize when calling the main command (in that order)

"Make clean"
Will clean out the /bin folder. I decided to leave the .txt and .csv files that were created so that they could be used
subsequently for the report.

"Make Experiment"
This was used by a bash script which was used to run the experiment. The experiment used its own
dedicated class called "MainExperiment.java". It made use of a QueryGenerator class (under ./src)

"Main Everything"
Cleaned, compiled and ran the experiment

"Main 2D"
Lastly, a 2D filter was implemented - sequential and parallel methods. Their speeds were not tested
(since this was outside of this assignment's scope). However, correctness was validated in the same
way as explained in the report
