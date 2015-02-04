CREATE CONSTRAINT ON (person:Person) ASSERT person.neogen_id IS UNIQUE;
CREATE CONSTRAINT ON (skill:Skill) ASSERT skill.neogen_id IS UNIQUE;
CREATE CONSTRAINT ON (company:Company) ASSERT company.neogen_id IS UNIQUE;
CREATE CONSTRAINT ON (country:Country) ASSERT country.neogen_id IS UNIQUE;