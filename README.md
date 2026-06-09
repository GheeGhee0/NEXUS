# NEXUS
NEXUS is a simple application coded in Java you simple drop my custom made .app file onto it and it runs app is inside of it
Inspired by how MacOS handles applications with folders that have .app extensions and how all apps are portable. I am having fun porting this feature as a tiny program to Windows.
It is much simpler than having to click 'next' a million times on a install wizard.

**Please** read the license to view redistrubution rules/permissions.

Below is a '.app' example like that you can make your own too!
To package it right-click on Contents folder an select compress to .ZIP. Then rename the .zip to .app.
The icon feature is not yet supported.

```
YourApp.app
│
├── Contents
│   │
│   ├── info.xml
│   │
│   ├── Windows
│   │   │
│   │   └── YourApp.jar
│   │
│   └── Resources
│       │
│       └── icon.png
```

and a simple info.xml example
```
<?xml version="1.0" encoding="UTF-8"?>

<App>
	<Name>My App</Name>

	<Version>0.0.1</Version>

	<Type>java</Type>

	<Entry>MyApp.jar</Entry>

  <Icon></Icon>
</App>
```
