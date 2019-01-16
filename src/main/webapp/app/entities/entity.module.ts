import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { Demo2UtilisateurModule } from './utilisateur/utilisateur.module';
import { Demo2GroupeModule } from './groupe/groupe.module';
import { Demo2FournisseurModule } from './fournisseur/fournisseur.module';
import { Demo2ProduitModule } from './produit/produit.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        Demo2UtilisateurModule,
        Demo2GroupeModule,
        Demo2FournisseurModule,
        Demo2ProduitModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Demo2EntityModule {}
