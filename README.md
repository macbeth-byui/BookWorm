## Overview

**Project Title**: 

BookWorm

**Project Description**:

Android App that will keep track of the books that you are reading.  Features include:

* Local Phone Storage
* Camera Integration for Book Covers
* Status Bar for Pages Read
* Select (and remember) a Book List Filter
* Select (and remember) a Color Scheme
* Integrated Streaming Classical Music 

**Project Goals**:

The goals of this project are to keep track of my reading, improve my Kotlin skills, and 
explore graphics using Jetpack Compose libraries.

## Instructions for Build and Use

Steps to build and/or run the software:

1. From Android Studio, build, load, and run the app on your phone or emulator

Instructions for using the software:

1. Use the 3 icons on the title bar to start/pause music, select a filter, or select a color scheme
2. Click on a book or press the (+) button to create a new book
3. Enter the book details.  Click on the picture to take a new picture with the camera
4. Select save to store on the phone, delete to remove from the phone, or cancel to make no changes
5. The tool bar on the bottom of the app will show the stats for the current filter applied
6. When listening to music, you can leave the app and control the music from the notification bar

## Development Environment 

To recreate the development environment, you need the following software and/or libraries with the specified versions:

* Android Studio (Merrkat version)
* Android SDK 35
* Additional Libraries (see libs.versions.toml):
	* `accompanistPermissions = "0.37.2"`
	* `agp = "8.9.2"`
	* `coilCompose = "2.6.0"`
	* `kotlin = "2.0.21"`
	* `coreKtx = "1.16.0"`
	* `junit = "4.13.2"`
	* `junitVersion = "1.2.1"`
	* `espressoCore = "3.6.1"`
	* `kotlinxSerializationJson = "1.6.3"`
	* `lifecycleRuntimeCompose = "2.8.7"`
	* `lifecycleRuntimeKtx = "2.8.7"`
	* `activityCompose = "1.10.1"`
	* `composeBom = "2025.04.01"`
	* `media3UiCompose = "1.6.1"`
	* `navigationComposeVersion = "2.8.9"`

## Useful Websites to Learn More

I found these websites useful in developing this software:

* [ExoPlayer](https://developer.android.com/media/implement/playback-app)
* [Accompanist Permissions](https://google.github.io/accompanist/permissions/)
* [Coil AsyncImage](https://coil-kt.github.io/coil/compose/)
* [Kotlin Serialization Json](https://kotlinlang.org/docs/serialization.html#serialize-and-deserialize-json)



## Future Work

The following items I plan to fix, improve, and/or add to this project in the future:

* [ ] Add more color schemes
* [ ] Support ability to remove current picture and display the default
