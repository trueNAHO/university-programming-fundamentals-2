# University: Programming Fundamentals 2 (02.2023 – 06.2023)

- [About](#about)
- [Requirements](#requirements)
- [Usage](#usage)
- [Related](#related)
- [Contributing](#contributing)
- [License](#license)

## About

A hardware farming simulator with planting, harvesting, inventory, and day-night
cycle functionality.

## Requirements

- [`make`](https://www.gnu.org/software/make)
- [`mvn`](https://github.com/apache/maven)

## Usage

This project provides the following [Makefile](Makefile) targets:

| Target         | Description                                                 |
|----------------|-------------------------------------------------------------|
| `make all`     | Default target that triggers `clean`, `compile`, and `run`. |
| `make clean`   | Remove build artifacts and dependencies.                    |
| `make compile` | Compile the application.                                    |
| `make format`  | Format the source code.                                     |
| `make run`     | Run the application.                                        |

## Related

- [Apache Maven](https://github.com/apache/maven)
- [GNU Make](https://www.gnu.org/software/make)
- [OpenJFX](https://github.com/openjdk/jfx)

## Contributing

To contribute, please review [our contribution
guidelines](docs/CONTRIBUTING.md).

## License

This project is licensed under [MIT](LICENSE).
