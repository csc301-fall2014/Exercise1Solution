# Exercise1

 * [Goals](#goals)
 * [First Steps](#first-steps)
 * [Your Task](#your-task)
 * [The Code](#the-code)
 * [The Development Process](#the-development-process)
 * [Team Deliverables](#team-deliverables)
 * [Individual Deliverables](#individual-deliverables)
 * [Marking Scheme](#marking-scheme)


## Goals ##
 * Coding warm-up: Java & JUnit.
 * Getting used to using Git & GitHub with a team.
 * Implementing and evaluating a *Test Driven Development* process.

## First Steps ##

0. Add your contact info to [`Contact.md`](Contact.md), so your teammates know how to get in touch with you.
1. Make sure you have [Git](http://git-scm.com/downloads) and [Eclipse](http://www.eclipse.org/downloads/) (I suggest the *Eclipse IDE for Java EE Developers* version) installed on your machine.
2. Clone this repository to your local machine.
3. Open the project in Eclipse
  * File --> Import --> Existing Project Into Workspace
  * Select the root of the repository (i.e. The folder containing pom.xml) as the root directory for the project.
  * **Important:** Make sure that the *Copy project into workspace* option is **unchecked**. 
4. Now that you have things set up, go through the code, and make sure you understand it.   
   If something doesn't make sense to you, talk about it with your teammates, TA's, or instructor.    
   We encourage you to use the [Discussion Board](http://piazza.com/utoronto.ca/fall2014/csc301/) for general questions (i.e. questions that are _not_ specific to your team).


## Your Task ##

For this project, you will implement (and test) a very simple trip advisor - Train companies offer direct routes between train stations, and your trip advisor finds a cheapest trip using these direct routes.

If you have taken CSC263/5, this problem should remind you of finding a shortest path in a Graph (with non-negative edge weight). In order to implement the trip advisor, you might want to read about [Dijkstra's algorithm](http://en.wikipedia.org/wiki/Dijkstra%27s_algorithm).
 
 Here are some of the assumptions/simplifications of the problem:
 * A company offers a single price per route. In other words, a company __cannot__ offer different prices for the same route.
 * A company doesn't have to offer routes from every station to every other station (i.e. some routes may not be available in all companies).
 * Routes are asymmetric. For example, a company could offer a direct route from Ottawa to Toronto without offering one from Toronto to Ottawa. A company might charge one price when going from Ottawa to Toronto and a different price when going from Toronto to Ottawa.
 * A _trip_ is a (non-empty) sequence of direct routes that connect two stations.    
   The direct routes in a trip can be from different companies.    
   For example, a trip from Toronto to Buffalo: Take *Via Rail* from Toronto to Niagara, and *Amtrak* from Niagara to Buffalo.


### Simple Example ###

* A company called *FastTrain* offers the following direct routes:
  * Toronto to Ottawa for 31$
  * Ottawa to Montreal for 25$
* A company called *SwiftRail* offers the following direct routes:
  * Toronto to Ottawa for 30$
  * Ottawa to Montreal for 26$

If our trip advisor (which uses both copmanies) is asked to find a cheapest trip from Toronto to Montreal, it should answer with:
 * Toronto to Ottawa, with *SwiftRail*, for 30$.
 * Ottawa to Montreal, with *FastTrain*, for 25$.

This is a very simple example, but your trip advisor should work with more complex data as well (e.g. Many trains, and many routes, and searching for trips that require more than 2 routes).


## The Code ##

Let's see what's already in the repository:
 * `README.md` contains the assignment handout that you are reading right now.
 * [`Contact.md`](Contact.md) - Your contact information.
 * [`Process.md`](Process.md) - Description of your software development process (that you will write as part of this exercise).
 * `.gitignore`, `pom.xml`, `.classpath`, and `.project` can be ignored for now. We will get into more details about some of these files, later in the course.
 * `src/main/java` a folder containing all of our Java code, except for our tests.
 * `src/test/java` a folder containing all of the Java code for our testing (i.e. JUnit test cases).
 * `src/test/resources` a folder containing all non-Java resources used in our testing.   
   An example for a non-Java resource is a text file containing data that will be loaded by our unit tests.


The code under `src/main/java` is organized into 3 classes:
 * `DirectRoute` 
 * `TrainCompany`
 * `MyTripAdvisor`
You will notice that the code uses very few comments. This is intentional, as reading code is an essential part of a software engineer's job. If you are not sure about something, please use the discussion board. If you think that your question reveals too much about your code, please email your TA/Instructor.

The code under `src/test` contains all of the test resources.    
 * You will notice that we have implemented a few tests, as well as some helper classes and methods. We just wanted to give you an idea of how one might make the task of writing tests a little easier.
 * Our implementation is not the only way to improve the process of writing tests (in fact, it has a few major problems). Later in the course, we will analyze some of the "mistakes" we've made with this simple implementation.


### Requirements ###

 * In `src/main/java`, implement all existing methods (and constructors), in all three classes, correctly.    
   You can add your own helper methods/classes if you wish.
 * Implement all of your unit tests in `TestDirectRoute`, `TestTrainCompany`, and `TestMyTripAdvisor` in the `src/test/java` folder.

Additional requirements:
 * Whenever a bad argument is passed to a method, your code should throw an _IllegalArgumentException_ (this is known as *programming defensively*).
   * Station names and train company names:
     * Must not be null.
     * Must contain at least one non-whitespace character.
     * Trailing white spaces are ignored (i.e. `" Via  "` and `"Via"` are considered the same name).
   * `DirectRoute`
     * Train company must not be null
     * Price must be non-negative
     * Two `DirectRoute` instances are equal if all of their properties (i.e. the values
       returned by getter methods) are equal.
   * `TrainCompany`
     * There should never be two `TrainCompany` instances with the same name.   
     That is, the second line of the following code should throw an IllegalArgumentException:

     ```
     TrainCopmany c1 = new TrainCompany("Via");
     TrainCopmany c2 = new TrainCompany("Via");
     ```

   * `MyTripAdvisor`
     * Adding a null company is not allowed.
 * In `TrainCompany.java` - Methods that return a `Collection<DirectRoute>`, must never return null (they can return an empty collection).
 


## The Development Process ##

You will use Git and GitHub in a Test Drive Development fashion.
You will have to use [Github issue management system](https://guides.github.com/features/issues/). This should help you manage your teams.

You should follow the following process:
 1. Student1 finds a bug, and 
    1. creates a branch.
    2. Adds a failing test case in that branch.
    3. Opens a corresponding issue on GitHub.
 2. Student2 picks up the opened issue, and
    1. Checks out the branch
    2. Fixes the code to pass the failing test
    3. Merges the branch
    4. updates the issue on GitHub

**Important:** Please try to be disciplined, and follow this workflow. We will look at your issues, and commit history in order to evaluate your teamwork. Note that issues can be used to keep track of tasks and not only bugs.

Notice that there are many aspects of the process for you to decide on. For example:
 * How to assign issues (e.g. Should the person who opens the ticket also assign it to someone, or should people just "pick up" an opened issue by assigning it to themselves).
 * Which labels, if any, do you want to define/use for your GitHub issues?
 * Should the description of an issue follow a certain format?
 * Do you want to define any milestones? (e.g. _Basic Implementation_, or _Write Report_)
 * How are you going to communicate?
 * Do you want to have a schedule?
 * etc.


## Team Deliverables ##

 * All team deliverables should be submitted to your *team repo*.    
 * When we run your application and/or testing code, we will use the code in your `master` branch.    
 * In [`Process.md`](Process.md), write a report that describes your team's development process.
   * Use Markdown to format things nicely. The specific format is up to you.
   * Here are some points that could give you an idea for what should be included in the report:
     * How did you communicated with one another? (e.g. email, shared doc, in-person meetings, online handouts)
     * Did you have a schedule?
     * How did you assign different tasks between group members?
     * How did you use GitHub issue management system?
     * Mini Case Study - "We decided to do X, because we thought Y, and, after trying it for a week, we think Z".
     * Anything else that might give your TA/Instructor an insight to your development process.
     * For every important decision that you made during the process, tell us __why__ you made it.    
       Please be honest (e.g. If you couldn't decide on something, and ended up tossing a coin, that's totally fine). 
       We are not grading you on _how good your decisions are_, we want you to make the decisions so that you can evaluate them, become aware of some of the difficulties Soft Dev teams go through, and think of ways to improve the process.
   * Please __keep it short__.    
     * Roughly 2-4 minutes read.
     * Don't repeat information that already appears in this handout, please tell us what we don't know.
     * If you have too many things to write, focus on the highlights (e.g. The best, worst, and most interesting decisions you've made).
   * Review your text for typos.
   * Focus on content, not power words.
   
     

## Individual Deliverables ##

* Create a file called `exercise1-report.md` in the root folder of your **personal repo**.
* In this file, answer the following three items:
   * Mention one of you team's decisions that, in hindsight, you think was good. Why do you think it was a good decision?
   * Mention one of you team's decisions that, in hindsight, you think was bad. Why do you think it was a bad decision?
   * Which one of your team members did you enjoy working with the most? And, why?
* Keep the format simple and __short__:
  * An unordered list containing 3 items.
  * Each item should be 1 to 3 sentences long.
  * For example:

    ```
    * Good - Meeting in person every day. We discovered a lot of minor bugs, and clarified issues during these meetings.
    * Bad - Writing all the tests, before doing any coding. We took too long writing the tests, and ended up not having enough time for everything else.
    * John, because he was the most responsive to emails.
    ```


## Marking Scheme ##

 * 80%, Team mark
   * 40%, How your code runs against our unit tests.
     * We will determine the exact function once we see the test results.
     * For now, you should know that if you pass all of our tests, you will get the full mark for this part. If you manage to fail all of our tests, you will get 0 for this part.
   * 25%, Report in `Process.md`
     * 5%, having a well-written report.
     * 5%, describing how the team members communicated with one another.
     * 5%, describing how you used the various tools (specifically, GitHub issue management system).
     * 10%, describing __why__ you made the decisions you've made.
   * 15%, How well your code (application and test) does against other teams' code.
     * For each pair of teams we will:
         * Test both implementations against both test suites.
         * Count the total number of bugs (from both test suites) found in each implementation.
         * The implementation with the smaller number of bugs wins.
         * If the number of bugs is the same, both teams win.
     * Your mark will be proportional to the number of teams you beat (e.g. if your team beats half the teams, and loses to the other half, you will get 7.5% out the 15%).
 * 20%, Individual mark
   * 10%, Active participation in the team's work (reflected by the number and frequency of your commits to the team's repo).
   * 10%, Report in `exercise1-report.md` (in your __personal repo__)

