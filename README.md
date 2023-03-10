# My Personal Project for CPSC 210

## Opportunities Management System

### **Task 2**

> **_Opportunities Management System_** is for students looking for off-campus
> opportunities or wanting to create opportunities for others. It facilitates adding
> information about students, their projects, internships and volunteering opportunities. 
> Users can find requirements, deadlines for opportunities, partners for projects
> and number of positions available for a certain opportunity. Aside from this, 
> students are able to filter out opportunities according to their interests. 
> I became interested to embark on this project since I am immensely interested 
> in EdTech and Career Guidance Counselling for high school students. I believe that, 
> with more open-source opportunities, students - be they undergraduates or high school 
> students - will be able to concentrate on their interests and apply for opportunities, 
> thus contributing positively to their career journeys.

### **Task 3**

#### _user stories_ :
- As a user, I want to be able to _**add an opportunity to the system**_
- As a user, I want to be able to _**view the list of opportunities in the platform**_
- As a user, I want to be able to _**mark an opportunity as available**_
- As a user, I want to be able to _**mark an opportunity as expired**_
- As a user, I want to be able to _**remove an opportunity from my system**_
- As a user, I want to be able to _**edit opportunities available**_

## **PHASE 2**
### **Task 2**
- As a user, I want to be able to _**save my opportunity list to file**_
- As a user, I want to be able to _**load my opportunity list from file when lists selected**_
- As a user, when I select the quit option from the application menu, 
I want to _**be reminded to save my opportunity list to file and have the option to do so or not.**_

## **PHASE 4**
### **Task 2**
Nothing is related to your events logged printed out when my program runs.

The event log isn’t just a listener or an aspect around data persistence. 
Event logs are useful for is consuming events from the system. In there, database only stores modifications, rather than the current state. 
The current state is the obtained after applying all the stored modifications until the present moment. 
This allows seeing the state of the data at any moment in the past. The data in event logs operate in non-data-modifying way,
where the database only stores modifications, rather than the current state. 
The current state is the obtained after applying all the stored modifications until the present moment. For this reason, 
events logged did not printed out.


## **PHASE 4**
### **Task 3**
Reflections on refactoring and improvements of design:
- Splitting ActionListener class in the GUI class into multiple separate classes to avoid 
overwhelming each button actions
- Implementing an abstract class 
- Refactor code to decrease coupling between OpportunityList and OpportunityPost
- During GUI construction, particularly creating posts page, there were many repetitive tasks in adding certain buttons
and other elements like JComboBox and converting it into

