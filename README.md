# nya

Introducing Nya, a platform for exchanging cryptocoins, renting your hardware
and simply having fun. Nya is an open source development.

The main user interface is command-line based and permits for users to
login/register, to send messages and follow the events across other channels
(twitter, IRC, telegram, ...) from inside one single location. Another aspect
is the possibility of renting your hardware in exchange for nya coins.

Effectively giving a second life to computer hardware available today.

![nya](https://github.com/nya-official/nya-server/blob/main/media/screenshots/screencast-2021-06-27.gif?raw=true)

Technology
----------
Following the principle of self-sustainability nya aims to incrementally grow
less dependent on centralized technologies. The storage of data is largely
based on [IPFS](https://ipfs.io/) (Interplanetary FileSystem), a storage where
each user can host the nya files that they want to keep (channels,
conversations, images) and keep this data alive even if the nya server one day
is blocked or disappears.

The programming language is plain Java. The reason is because every programmer
can quickly understand the code and the binaries will run inside inexpensive
hardware such as Android without difficulties. Another advantage of Java is
possibility to bridge libraries from other programming languages thanks to
its Java Virtual Machine, with the option of compiling the code directly to
binaries using [GraalVM](https://www.graalvm.org/).

User interface gives preference first to the command line, second to the API
and third to other integrations provided by volunteers to interact with nya.



### Getting started
Nya is developed using Maven, Netbeans, Git and Java 11.
Please install Netbeans with JDK 11, then download the MASTER branch.
Open the project from NetBeans and should be configured.
Press the play button on the IDE to launch the server.


### License
All code is published under the Apache-2.0 license.
Third-party code follows licenses compatible with the main license.


### Code contributions
You are welcome to join, we need developers (see the bugtracker for ideas).
Code contributions from developers are made under the Apache-2.0 license terms.
