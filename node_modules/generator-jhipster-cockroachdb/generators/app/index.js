const chalk = require('chalk');
const packagejs = require('../../package.json');
const semver = require('semver');
const BaseGenerator = require('generator-jhipster/generators/generator-base');
const jhipsterConstants = require('generator-jhipster/generators/generator-constants');

module.exports = class extends BaseGenerator {
    get initializing() {
        return {
            init(args) {
                if (args === 'default') {
                    // do something when argument is 'default'
                }
            },
            readConfig() {
                this.jhipsterAppConfig = this.getJhipsterAppConfig();
                if (!this.jhipsterAppConfig) {
                    this.error('Can\'t read .yo-rc.json');
                }
                if (this.jhipsterAppConfig.devDatabaseType !== 'postgresql' ||
                    this.jhipsterAppConfig.prodDatabaseType !== 'postgresql') {
                    this.error('You need to have Postgresql as development and production database type');
                }
            },
            displayLogo() {
                // it's here to show that you can use functions from generator-jhipster
                // this function is in: generator-jhipster/generators/generator-base.js
                this.printJHipsterLogo();

                // Have Yeoman greet the user.
                this.log(`\nWelcome to the ${chalk.bold.yellow('JHipster cockroachdb')} generator! ${chalk.yellow(`v${packagejs.version}\n`)}`);
            },
            checkJhipster() {
                const currentJhipsterVersion = this.jhipsterAppConfig.jhipsterVersion;
                const minimumJhipsterVersion = packagejs.dependencies['generator-jhipster'];
                if (!semver.satisfies(currentJhipsterVersion, minimumJhipsterVersion)) {
                    this.warning(`\nYour generated project used an old JHipster version (${currentJhipsterVersion})... you need at least (${minimumJhipsterVersion})\n`);
                }
            }
        };
    }

    prompting() {
        const prompts = [
            {
                type: 'input',
                name: 'message',
                message: 'This will add/update CockroachDB database migration files (Y/n)',
                default: 'Y'
            }
        ];

        const done = this.async();
        this.prompt(prompts).then((props) => {
            this.props = props;
            // To access props later use this.props.someOption;

            done();
        });
    }

    writing() {
        // function to use directly template
        this.template = function (source, destination) {
            this.fs.copyTpl(
                this.templatePath(source),
                this.destinationPath(destination),
                this
            );
        };

        // read config from .yo-rc.json
        this.baseName = this.jhipsterAppConfig.baseName;
        this.packageName = this.jhipsterAppConfig.packageName;
        this.packageFolder = this.jhipsterAppConfig.packageFolder;
        this.clientFramework = this.jhipsterAppConfig.clientFramework;
        this.clientPackageManager = this.jhipsterAppConfig.clientPackageManager;
        this.buildTool = this.jhipsterAppConfig.buildTool;

        // use function in generator-base.js from generator-jhipster
        this.angularAppName = this.getAngularAppName();

        // use constants from generator-constants.js
        const resourceDir = jhipsterConstants.SERVER_MAIN_RES_DIR;

        // variable from questions
        this.message = this.props.message;
        this.yes = ['Y', 'y'].includes(this.message);

        if (!this.yes) {
            this.warning('Installation aborted');
            return;
        }

        this.template('src/main/resources/config/liquibase/master.xml.ejs', `${resourceDir}config/liquibase/master.xml`);
        this.template('src/main/resources/config/liquibase/changelog/initial_schema.sql.ejs', `${resourceDir}config/liquibase/changelog/00000000000000_initial_schema.sql`);
    }

    install() {
    }

    end() {
        if (this.yes) {
            this.log();
            this.log('--- Please update your application-dev.yml ---');
            this.log(`${chalk.green.bold(`spring.datasource.url: jdbc:postgresql://localhost:26257/${this.baseName}?sslmode=disable`)}`);
            this.log();
            this.log('--- Please update your application-prod.yml ---');
            this.log(`${chalk.green.bold(`spring.datasource.url: jdbc:postgresql://localhost:26257/${this.baseName}`)}`);
            this.log();
            this.log(`${chalk.green.bold('You can remove 00000000000000_initial_schema.xml')}`);
        }
    }
};
