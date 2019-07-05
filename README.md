# MVVM Setup
Very basic MVVM project, showing basic setup of Android MVVM architecture using lifecycle extensions for live data and RxJava and RxBinding for observables
Backend access to the database is handled via Room
The app itself is just three incredibly basic activities, building up into a rudementary calorie counter

- The splash screen loads the stored user information from Shared Preferences and navigates us to the next activity accordingly
- The initializer screen gives us an input field to set our desired daily calorie intake
- The counter screen provides a button for tracking your current calorie intake and reads/writes these values to an SQLite database via Room
- The options screen is a very simple form for editing the user's Shared Preferences

 Very much an MVVM example that is a work in progress. The overall feature set is still incredibly simple
