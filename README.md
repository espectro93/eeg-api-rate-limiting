# Allthingssmart Energize

This is a small example project that utilizes german EEG data until 2021 for various kwp ranges. Access is regulated through different Plans for api usage.

- [Installation](#installation)
- [Usage](#usage)
- [Further work](#furtherwork)

---

## Installation

- Clone repository
- mvn package
- cd into target & java -jar <file>

## Usage
- There is a GET endpoint /eeg with mandatory parameters for kwp and date

## Furtherwork
- Not all eeg options are implemented yet but already parsed, more request parameters could specify those values
- The Pricing Plan for api usage could be extended with a ApiKey model that utilizes a repository and the creation of keys on signup (those endpoints for customers are also missing)
