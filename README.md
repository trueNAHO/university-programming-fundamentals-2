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
- [License](#license)
<!--toc:end-->

## Requirements

- [Apache Maven](https://github.com/apache/maven)

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

## License

This project is licensed under [MIT](LICENSE).
