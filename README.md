# MadridShopsAndroid

Create a mobile application to display information of Shops in Madrid, even when the user has no Internet connection. Shops should be shown in a Map.

## Screenshots
Logo | Main  |  Shops list | Detail
------------ | ------------- | ------------- | -------------
<img src = "https://github.com/AbGLopez/MadridShopsAndroid/blob/master/screenshots/shoplogo.jpeg" width="250px"> | <img src = "https://github.com/AbGLopez/MadridShopsAndroid/blob/master/screenshots/main_screen.jpeg" width="250px"> | <img src = "https://github.com/AbGLopez/MadridShopsAndroid/blob/master/screenshots/shop_list.jpeg" width="250px"> | <img src = "https://github.com/AbGLopez/MadridShopsAndroid/blob/master/screenshots/shop_detail.jpeg" width="250px">


## Requirements

1. When starting the App for the first time, if there's Internet connection it will download all information from the Shops access point (see below), including all images.
2. The App will cache everything locally: images, data, etc. Even images of the maps.
3. Cache is never invalidated, so once everything has been saved, set a flag and never ever access to the network again.
4. If there's no Internet connection a message will be shown to the user.
5. While caching the App will show an Activity indicator or other loader. Until you finish caching you don't get to the Main menu.
6. The app will have a main menu screen where we'll add one button & a logo. The button takes us to the list of shops.
7. The list of Shops will show in the upper 50% screen a map with one pin for each shop.
8. The list of Shops will show in the lower 50% screen. Logo to the left, background image taking all the row, shop name in the front. Tapping a row takes us to the detail shop screen.
9. All info should be read from a Core Data database.
10. If you tap on a pin in the map a callout will open.
shop name. Taping the callout takes us to the detail shop screen.
11. The map will be always centered in madrid, showing also the user location.
12. All data is at least in Spanish & English: should cache all and display in Spanish (if that's our phone's language) or English otherwise.
13. Shop detail screen should show shop name, description, address, and a map showing the shops's location without any pin.

## Web services
URL MapCache API
>"https://maps.googleapis.com/maps/api/staticmap?%25&size=320x220&scale=2&markers="

[URL Shops API](https://madrid-shops.com/json_new/getShops.php)


## Technologies
* Kotlin
* Volley
* Piccaso

## Requires

- [Get your Google API key](https://developers.google.com/maps/?hl=es-419)


- Create a file at `/MadridShopsAndroid/app/src/main/res/values` folder. Add your google api:

```
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="ApiKey">GOOGLE_MAPS_API_KEY</string>
</resources>
```