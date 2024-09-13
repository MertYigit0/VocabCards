
# Overview
The Vocabulary Cards application is an Android app designed to help users learn and manage vocabulary words. It provides a user-friendly interface for viewing and organizing vocabulary words, with features to view details, listen to pronunciation, and navigate between different lists of words.



<img src="https://github.com/user-attachments/assets/5c8daf4b-8a25-4322-a8d1-1fab68fad298" width="200" height="400">
<img src="https://github.com/user-attachments/assets/8344efdf-9081-4d63-8b61-0309daac3894" width="200" height="400">
<img src="https://github.com/user-attachments/assets/5455372c-48b0-481a-83f0-f0125be6f4f2" width="200" height="400">
<img src="https://github.com/user-attachments/assets/27899487-038a-4ea4-b929-2805eb14e3c3" width="200" height="400">

# Features
View Word Lists: Display and navigate through lists of vocabulary words.

Word Details: View detailed information about each word, including pronunciation and translations.

Audio Pronunciation: Listen to the pronunciation of words.

Smooth Animations: Enjoy smooth transitions between fragments using custom animations.


# Technologies and Libraries

## Core Technologies
Kotlin: The primary programming language used for developing the application.

MVVM Architecture: Model-View-ViewModel architecture is employed to separate concerns and manage UI-related data in a lifecycle-conscious way.

## Libraries
Retrofit: Used for handling network requests and parsing JSON responses from the dictionary API.

Lottie: Utilized for displaying animations in the app.

ViewBinding: Simplifies the process of accessing and manipulating views in the layout.

Navigation Component: Manages fragment transactions and navigation within the app, including custom animations.

# Project Structure
ui: Contains the user interface components, including Fragments and ViewModels.

learnedlist: Contains the LearnedListFragment and associated ViewModel.

wordlist: Contains the WordListFragment and associated ViewModel.

worddetail: Contains the WordDetailFragment and associated ViewModel.

data: Contains data-related classes, including models and repository.


model: Defines data models such as Word.

repository: Manages data operations and interacts with local and remote data sources.

network: Contains API service definitions and Retrofit instance.

utils: Contains utility classes like PrefsHelper for managing shared preferences.


