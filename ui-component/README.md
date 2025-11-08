# UI-Component Module

The `ui-component` module is a rich toolkit of Jetpack Compose components that are designed to be used throughout the application. It provides a consistent look and feel, and it promotes code reuse by providing a set of common UI elements.

## Gradle Dependency

To use this module, add the following dependency to your `build.gradle` file:

```groovy
implementation "building.blocks:ui-component:<latest version>"
```

## Core Features

### Navigation

The module provides a set of components for building an adaptive navigation system that works on both phones and tablets.

- **`FleaMarketNavigationBar`**: A composable that displays either a `FMBottomNavigation` or a `FMNavigationRail` depending on the screen width.
- **`FMBottomNavigation`**: A bottom navigation bar for use on smaller screens.
- **`FMNavigationRail`**: A navigation rail for use on larger screens.
- **`ScreenWithBottomBar`** and **`ScreenWithNavigationRail`**: Scaffold-like composables that provide a screen layout with either a bottom navigation bar or a navigation rail.

### Snackbar System

The module provides a centralized system for displaying snackbars.

- **`SnackbarDelegate`**: A singleton that manages and displays snackbars with different types (default, error, success).
- **`FMSnackbarHost`**: A custom `SnackbarHost` that displays snackbars with different icons and colors based on the `SnackbarType` from the `SnackbarDelegate`.
- **`SnackbarDetails`** and **`SnackbarWithActionDetails`**: Data classes that hold the details for a snackbar.
- **`SharedUIController`**: An interface for controlling shared UI elements, like snackbars, from different parts of the application.

### Basic Components

The module provides a set of basic UI components that are styled to match the application's theme.

- **`FMAppBar`**: A custom `TopAppBar` for the application.
- **`FMButton`** and **`FMOutlinedButton`**: Custom buttons with a specific capsule shape and styling.
- **`LazyImage`**: A composable that uses Coil to display an image from a URL, with a shimmer effect for the loading state and a gray background for the error state.
- **`EmptyLayout`**: A composable that displays a message and an icon, for use when there is no data to display.
- **`ErrorLayout`**: A composable that displays an error message, an icon, and an optional retry button.
- **`PageIndicator`**: A component that displays a row of dots to indicate the current page in a pager.
- **`Stepper`**: A component for increasing and decreasing a quantity.

### Specialized Components

The module also provides some more specialized components with rich animations.

- **`HeartToggleButton`**: A toggle button that animates between a filled and bordered heart icon, with a size and color animation.
- **`AddToCartButton`**: A complex and stateful button that shows different animations for the initial, loading, and result states.

## How to Use

To use the components in this module, you can simply add the `ui-component` module as a dependency to your app module. You can then use the composables in your screens as needed. For the navigation components, you will also need to provide a `NavController` and a list of `NavigationBarScreen`s.
