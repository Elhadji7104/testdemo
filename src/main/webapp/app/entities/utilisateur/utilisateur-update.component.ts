import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IUtilisateur } from 'app/shared/model/utilisateur.model';
import { UtilisateurService } from './utilisateur.service';
import { IGroupe } from 'app/shared/model/groupe.model';
import { GroupeService } from 'app/entities/groupe';

@Component({
    selector: 'jhi-utilisateur-update',
    templateUrl: './utilisateur-update.component.html'
})
export class UtilisateurUpdateComponent implements OnInit {
    utilisateur: IUtilisateur;
    isSaving: boolean;

    groupes: IGroupe[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected utilisateurService: UtilisateurService,
        protected groupeService: GroupeService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ utilisateur }) => {
            this.utilisateur = utilisateur;
        });
        this.groupeService.query().subscribe(
            (res: HttpResponse<IGroupe[]>) => {
                this.groupes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.utilisateur.id !== undefined) {
            this.subscribeToSaveResponse(this.utilisateurService.update(this.utilisateur));
        } else {
            this.subscribeToSaveResponse(this.utilisateurService.create(this.utilisateur));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IUtilisateur>>) {
        result.subscribe((res: HttpResponse<IUtilisateur>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackGroupeById(index: number, item: IGroupe) {
        return item.id;
    }
}
