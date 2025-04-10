# Users_Import_Java

This is a java client that consolidates user data from two different files, validates userid interacting with the [Piano](https://piano.io/) Publisher API and finally creates an output file.


## 📌 Features

- Connects to the Piano API and fetches user data
- Filters and accesses specific user information
- Loads local CSV files (userdata_A.csv and userdata_B.csv)
- Adjust user_id for incorrect records based on system information
- Creates an output file (users_output.csv)


## 🛠️ Tech Stack

- `com.opencsv:opencsv:5.7.1`
- `com.fasterxml.jackson.core:jackson-databind:2.13.3`

You can include them via Maven or download the JARs and add them to your classpath.

Maven `pom.xml` Snippet

<dependencies>
    <dependency>
        <groupId>com.opencsv</groupId>
        <artifactId>opencsv</artifactId>
        <version>5.7.1</version>
    </dependency>
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.13.3</version>
    </dependency>
</dependencies>

## 📦 Installation

1. Clone the repository:

```bash
git clone https://github.com/matiasjanssen/user_data_import_java.git
cd user_data_import_java
```

2. Install dependencies:

Download dependencies (using Maven or manually)

## 🚀 Usage

Make sure your local directory includes:

- `userdata_A.csv`
- `userdata_B.csv`

Then compile and run:

javac -cp "lib/*" Users_Import_Java.java
java -cp ".:lib/*" Users_Import_Java
📝 Replace lib/* with the actual path where your JAR files are stored.

