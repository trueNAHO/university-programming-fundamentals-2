<h1 align="center">
  RPG Laboratory
  <h3 align="center">Hardware Farm</h3>
</h1>

<!--toc:start-->
- [Requirements](#requirements)
- [Installation](#installation)
- [Usage](#usage)
- [Related](#related)
- [Contributing](#contributing)
- [Contributors and Contributions](#contributors-and-contributions)
  - [Noah Pierre Biewesch](#noah-pierre-biewesch)
  - [Gabriel Ny aintsoa Leclerc](#gabriel-ny-aintsoa-leclerc)
  - [Jean Humbert](#jean-humbert)
- [License](#license)
<!--toc:end-->

## Requirements

- [`mvn`](https://github.com/apache/maven)

## Installation

```bash
git clone https://github.com/uni-lu/rpg-windows-tolerates-arch
```

## Usage

The project includes a [Makefile](Makefile) with the following targets:

- `make all`
  - Default target that triggers other targets: `clean`, `compile`, and `run`.
- `make clean`
  - Remove build artifacts and dependencies.
- `make compile`
  - Compile the project.
- `make format`
  - Format the source code.
- `make run`
  - Run the project.

## Related

- [Apache Maven](https://github.com/apache/maven)
  - Software project management and comprehension tool.
- [Commitizen](http://commitizen.github.io/cz-cli)
  - Simple commit conventions for internet citizens.
- [OpenJFX](https://github.com/openjdk/jfx)
  - JavaFX mainline development.

## Contributing

For information on contributing to this project, please refer to
[CONTRIBUTING.md](docs/CONTRIBUTING.md).

## Contributors and Contributions

### Noah Pierre Biewesch

- Initial Framework Setup
  - Established the foundation of the application by creating the main game
    loop, implementing Blocks, Day/Night Cycle, Player entity, the field, and
    the input handler. Designed the player and blocks as state machines, with
    the input handler processing Command objects.
- TextBox Implementation
  - Integrated the TextBox functionality, enabling the display of timed messages
    for enhanced in-game communication and interaction.
- Optimization
  - Significantly improved the JavaFX startup time, optimizing the application's
    performance and efficiency.

### Gabriel Ny aintsoa Leclerc

- Framework Elaboration and Extension
  - Expanded and enhanced the initial framework to deliver substantial graphical
    improvements. Upgraded the visual experience by replacing uniformly colored
    blocks with captivating graphics. Led the creation and integration of images
    and sprites utilized throughout the game.
- Inventory Implementation
  - Developed a comprehensive inventory system, expanding the Field to
    accommodate a list of plants instead of blocks. Introduced a new plant class
    for increased control and functionality. Implemented the Inventory as a
        state machine, featuring items and slots.
- Sprites and Items
  - Added a diverse range of sprites and items to elevate the visual quality and
    immersion of the gameplay.
- Plant Interaction
  - Implemented intuitive mechanics for planting and harvesting, enabling player
    engagement with the in-game plant life.
- Bug Fixes and Improvements
  - Addressed issues related to sprites, inventory functionality, and plant
    mechanics to ensure a seamless and enjoyable gaming experience.

### Jean Humbert

- House Collision Implementation
  - Integrated house collision functionality, prohibiting the player from
    traversing graphical objects and ensuring realistic interactions within the
    game environment.
- TextBox and Code Documentation
  - Ensured high code quality by actively documenting the project's source code,
    including comprehensive documentation for the TextBox and its associated
    components.
- Bug Fixes and Improvements
  - Resolved issues pertaining to house collision, enhanced the functionality of
    the TextBox, and contributed to overall code quality through detailed
    documentation and comments.
- Code Quality Assurance
  - Ensured clarity and improved collaboration among team members by diligently
    documenting the project's source code.

## License

This project is licensed under [MIT](LICENSE).
