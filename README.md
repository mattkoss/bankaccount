#### Bank account application
#### Build
Application uses Gradle build system.

Run **gradlew tasks** to see a list of all available tasks.

Run **gradlew build** to build the application and execute all tests.

#### Start
Run **gradlew run** to start the application, it will listen on port 8080.

###### Logs

Logging output is available in the file **bankaccount.log** in the current directory.

Logging levels can be adjusted in the **src/main/resources/logback.xml** file

#### Sample requests

Following endpoints work:

To retrieve account balance for a respective account:

GET http://localhost:8080/api/account-balance?sortCode=40-40-40&accountNumber=12345678

GET http://localhost:8080/api/account-balance?sortCode=50-50-50&accountNumber=98765432

To action a transfer:

POST http://localhost:8080/api/transfer

with a payload like this:

```
{
    "sourceAccountBalance": {
        "amount": 103,
        "currency": "GBP"
    },
    "targetAccountBalance": {
        "amount": 476,
        "currency": "GBP"
    }
}
```

#### Design considerations
###### Choice of frameworks
I am using Jersey framework for REST endpoints, with the embedded jetty web server.

For dependency injection, I am using Jersey's HK2 package.

For testing - JUnit5 and Mockito.

###### Application bootstrapping and wiring
Due to dependency injection, a simple wiring config is required. This is encapsulated in the _BankAccountConfig_ and _BankAccountBinder_ classes.

Other classes then simply use the _@Inject_ annotation to plug in any beans they need.

###### Persistence
_InMemoryAccountRepository_ class simply uses a pre-defined list of accounts.

I have specifically not implemented any addition or deletion of accounts, as this was not requested.

###### Locking
_AccountLockingService_ is used to perform account locking in order to enable concurrent access.

In order to keep locking manageable, finite number of locks is used.
This will result in situations where sometimes multiple accounts are locked by a single lock.
However, if number of accounts is fairly low, then this number can be increases and mitigate/remove this issue.   

_ReadWriteLock_ locks are used to ensure concurrent read access when no writing occurs.

