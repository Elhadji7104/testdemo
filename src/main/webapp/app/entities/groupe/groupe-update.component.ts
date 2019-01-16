import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IGroupe } from 'app/shared/model/groupe.model';
import { GroupeService } from './groupe.service';

@Component({
    selector: 'jhi-groupe-update',
    templateUrl: './groupe-update.component.html'
})
export class GroupeUpdateComponent implements OnInit {
    groupe: IGroupe;
    isSaving: boolean;

    constructor(protected groupeService: GroupeService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ groupe }) => {
            this.groupe = groupe;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.groupe.id !== undefined) {
            this.subscribeToSaveResponse(this.groupeService.update(this.groupe));
        } else {
            this.subscribeToSaveResponse(this.groupeService.create(this.groupe));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IGroupe>>) {
        result.subscribe((res: HttpResponse<IGroupe>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
