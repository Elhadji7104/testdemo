import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Demo2SharedModule } from 'app/shared';
import {
    GroupeComponent,
    GroupeDetailComponent,
    GroupeUpdateComponent,
    GroupeDeletePopupComponent,
    GroupeDeleteDialogComponent,
    groupeRoute,
    groupePopupRoute
} from './';

const ENTITY_STATES = [...groupeRoute, ...groupePopupRoute];

@NgModule({
    imports: [Demo2SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [GroupeComponent, GroupeDetailComponent, GroupeUpdateComponent, GroupeDeleteDialogComponent, GroupeDeletePopupComponent],
    entryComponents: [GroupeComponent, GroupeUpdateComponent, GroupeDeleteDialogComponent, GroupeDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Demo2GroupeModule {}
