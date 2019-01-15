import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { ProdConfig } from './blocks/config/prod.config';
import { Demo2AppModule } from './app.module';

ProdConfig();

if (module['hot']) {
    module['hot'].accept();
}

platformBrowserDynamic()
    .bootstrapModule(Demo2AppModule, { preserveWhitespaces: true })
    .then(success => console.log(`Application started`))
    .catch(err => console.error(err));
