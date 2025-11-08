# BuildingBlocks Library

The BuildingBlocks Library is a collection of foundational modules designed to streamline Android application development. It offers a suite of tools and components that address various aspects of app development, promoting code reuse and consistency across projects.

## Modules Overview

The library encompasses the following modules:

1. **Foundation**: Provides essential base classes and utilities that serve as the building blocks for other modules. You can find the detailed documentation of it [here](https://gaurangshaha.github.io/BuildingBlocks/foundation/index.html).

2. **Networking**: Facilitates network operations, offering tools for API interactions, data fetching, and network-related utilities. You can find the detailed documentation of it [here](https://gaurangshaha.github.io/BuildingBlocks/networking/index.html).

3. **Test Common**: Contains testing utilities and base classes to support unit and integration testing, ensuring code reliability and robustness. You can find the detailed documentation of it [here](https://gaurangshaha.github.io/BuildingBlocks/test-common/index.html).

4. **UI Common**: Offers building blocks and helper methods for UI layer. You can find the detailed documentation of it [here](https://gaurangshaha.github.io/BuildingBlocks/ui-common/index.html).

5. **UI Component**: Includes a set of customizable UI elements and widgets that can be readily integrated into applications to enhance user experience. You can find the detailed documentation of it [here](https://gaurangshaha.github.io/BuildingBlocks/ui-component/index.html).

## Getting Started

You can integrate the BuildingBlocks Library into your project in two different ways:

1. Copying the source code directly into your project and adding those module as your gradle dependency.

2. Adding the library as gradle dependency .
      These libraries are hosted on Github packages and to use them 
      - Generate a personal access token on Github as mentioned [here](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/managing-your-personal-access-tokens#creating-a-personal-access-token-classic). Make sure that you allows permission for `read:packages`.
      - Create two properties `gpr.user` and `gpr.key` in you `local.properties`, the value will be Github user name and access token generated above step.
      - Add maven block in your setting.gradle file. (Please keep the existing configuration in `repositories` block as is and add the new block)
      ```Groovy
      dependencyResolutionManagement {
          repositories {
              maven {
                  url 'https://maven.pkg.github.com/GaurangShaha/BuildingBlocks'
                  credentials {
                      def propertiesFile = new File('local.properties')
                      Properties props = new Properties()
                      props.load(new FileInputStream(propertiesFile))
      
                      username props.getProperty("gpr.user")
                      password props.getProperty("gpr.key")
                  }
              }
          }
      }
      ```
      - In your project add the following gradle dependencies
          1.  **Foundation**: ```implementation building.blocks:foundation:<latest version>```
          2.  **Networking**: ```implementation building.blocks:networking:<latest version>```
          3.  **Test Common**: ```implementation building.blocks:test-common:<latest version>```
          4.  **UI Common**: ```implementation building.blocks:ui-common:<latest version>```
          5.  **UI Component**: ```implementation building.blocks:ui-component:<latest version>```

You are all set to use these building blocks to create an awesome application.

## Documentation

Comprehensive documentation for all public classes and functions is available [here](https://gaurangshaha.github.io/BuildingBlocks/index.html). This documentation provides detailed insights into the library's components, their usage.

## Samples

You can find the working sample of these libraries [here](https://github.com/GaurangShaha/FleaMarket). FleaMarket is and e-commerce app designed with compose. It uses combination of [FakeStoreApi](https://fakestoreapi.com/) and local database to achieve the functionalities.

## License

This project is licensed under the Apache-2.0 License. For more details, refer to the [LICENSE](https://github.com/GaurangShaha/BuildingBlocks/blob/main/LICENSE) file in the repository.
