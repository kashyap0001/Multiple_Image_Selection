# Multiple Image Selection

## How to add dependency?
This library is not released in Maven Central, but instead you can use [JitPack](https://jitpack.io)

add remote maven url in `allprojects.repositories`

```groovy
allprojects {
	repositories {
		maven { url "https://jitpack.io" }
	}
}
```

then add a library dependency

```groovy
dependencies {
	implementation 'com.github.kashyap0001:Multiple_Image_Selection:1.0.1'
}
```

```

startActivityForResult(Select_multiple.CreateSelection(context),request_code);

```

## Screenshots:-
### List of Folders
![List_of_Folders.jpg](https://github.com/kashyap0001/Multiple_Image_Selection/blob/master/List_of_folders.jpg)

### Select Multiple Images
![Selection_area.jpg](https://github.com/kashyap0001/Multiple_Image_Selection/blob/master/Selection_area.jpg)

### remove images from selection
![Selected_image.jpg](https://github.com/kashyap0001/Multiple_Image_Selection/blob/master/Selected_image.jpg)


