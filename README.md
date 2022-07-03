
<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->
[![MIT License][license-shield]][license-url]
[![LinkedIn][linkedin-shield]][linkedin-url]

[![Build status](https://build.appcenter.ms/v0.1/apps/cda7635f-622f-499d-9bb4-61a0ce93bfa9/branches/appcenter/badge)](https://appcenter.ms)


<!-- PROJECT LOGO -->
<br />
<p align="center">
  <a href="https://github.com/dev-loyde/TedStat">
    <img src="https://github.com/dev-loyde/TedStat/blob/master/app/src/main/res/drawable/launch_logo.png" alt="Logo" width="80" height="80">
  </a>

  <h3 align="center">TedStat covid19 Safety and Statistics App</h3>

  <p align="center">
    An awesome project that help you to easily find information and statistics on the covid19 pandemic!
    <br />
    <a href="https://install.appcenter.ms/users/dev_loyde/apps/tedstat/distribution_groups/public_release">View Demo</a>
    ·
    <a href="https://github.com/dev-loyde/TedStat/issues">Report Bug</a>
    ·
    <a href="https://github.com/dev-loyde/TedStat/issues">Request Feature</a>
  </p>
</p>



<!-- TABLE OF CONTENTS -->
## Table of Contents

* [About the Project](#about-the-project)
  * [Built With](#built-with)
* [Getting Started](#getting-started)
  * [Installation](#installation)
* [Contributing](#contributing)
* [License](#license)
* [Contact](#contact)
* [Acknowledgements](#acknowledgements)



<!-- ABOUT THE PROJECT -->
## About The Project

![App Walthrough Screen Shot][product-walkthrough]
![App Screen Shot][product-screenshot]

There are many great covid19 resources/information apps out there, however,I decided to create this one in order to further help in making information readily and easily accessible to everyone.
We need to be very informed at all times with happenings around us inorder for some reasons

Here's why:
* To help provide useful information with friends and family to keep everyone safe
* To be updated with statistical cases trend around us
* To easily get access to advisories and faq's from trusted sources

Of course, no one app will serve all needs since everyone needs may be different. So I'll be adding more features in the near future. You may also suggest changes by forking this repo and creating a pull request or opening an issue.

![App Screen Shot][product-dark]

A list of commonly used resources that I find helpful are listed in the acknowledgements.

### Built With
This App and its web scaper for fetching the online informations and statistics was Developed with.
* [Kotlin](https://kotlinlang.org)
* [Node Js](https://nodejs.org)


<!-- GETTING STARTED -->
## Getting Started

To get a local copy up and running follow these simple example steps.

### Installation
- Request a free API Key and Server base url
- Clone the repo
- Clone this repository and import into Android Studio
- Android studio version 4.0.1
- build gradle version 4.0.1
- compileSdkVersion 30
- kotlin version 1.3.72-release-Studio 4.0-5
- copy sample.env.properties into a new file env.     properties in project app folder
- request api keys
- Add build config variables
```JS
   API_KEY = 'ENTER YOUR API KEY';
   BASE_URL = 'ENTER SERVER BASEURL';
   APP_CENTER_KEY = 'ENTER YOUR SECRET KEY'
```
- build and run project.

### Generating Signed Apk 
- From Android Studio:
- Build menu
- Generate Signed APK...
- Fill in the keystore information 


<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to be learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request



<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE` for more information.



<!-- CONTACT -->
## Contact

Oiku Thaddeus - [@dev_loyde](https://twitter.com/dev_loyde) 
Project Link: [https://github.com/dev-loyde/TedStat](https://github.com/dev-loyde/TedStat)



<!-- ACKNOWLEDGEMENTS -->
## Acknowledgements
* [Lottie Animations](https://lottiefiles.com)
* [Wikipedia](https://en.wikipedia.org/wiki/Template:COVID-19_pandemic_data)
* [NCDC](https://covid19.ncdc.gov.ng)
* [BING SEARCH API](https://azure.microsoft.com/en-us/services/cognitive-services/bing-web-search-api/)
* [ExpansionPanel](https://github.com/florent37/ExpansionPanel)
* [Shape Of View](https://github.com/florent37/ShapeOfView)
* [Material Design](https://pages.github.com)
* [MP Charts](https://github.com/PhilJay/MPAndroidChart)
* [Debug DB](https://github.com/amitshekhariitbhu/Android-Debug-Database)
* [Shimmer Android](https://github.com/facebook/shimmer-android)



<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/othneildrew/Best-README-Template.svg?style=flat-square
[contributors-url]: https://github.com/othneildrew/Best-README-Template/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/othneildrew/Best-README-Template.svg?style=flat-square
[forks-url]: https://github.com/othneildrew/Best-README-Template/network/members
[stars-shield]: https://img.shields.io/github/stars/othneildrew/Best-README-Template.svg?style=flat-square
[stars-url]: https://github.com/othneildrew/Best-README-Template/stargazers
[issues-shield]: https://img.shields.io/github/issues/othneildrew/Best-README-Template.svg?style=flat-square
[issues-url]: https://github.com/othneildrew/Best-README-Template/issues
[license-shield]: https://img.shields.io/github/license/othneildrew/Best-README-Template.svg?style=flat-square
[license-url]: https://github.com/othneildrew/Best-README-Template/blob/master/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=flat-square&logo=linkedin&colorB=555
[linkedin-url]: www.linkedin.com/in/thaddeus-oseghale
<!-- [product-screenshot]: https://github.com/dev-loyde/TedStat/blob/master/screenshots/ted-stat-screenshot.png -->
[product-screenshot]: screenshots/ted-stat-screenshot.png
[product-walkthrough]: screenshots/ted-stat-walkthrough.png
[product-dark]: screenshots/ted-stat-showcase-dark.png
