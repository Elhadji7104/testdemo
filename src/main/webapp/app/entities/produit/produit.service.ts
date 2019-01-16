import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProduit } from 'app/shared/model/produit.model';

type EntityResponseType = HttpResponse<IProduit>;
type EntityArrayResponseType = HttpResponse<IProduit[]>;

@Injectable({ providedIn: 'root' })
export class ProduitService {
    public resourceUrl = SERVER_API_URL + 'api/produits';

    constructor(protected http: HttpClient) {}

    create(produit: IProduit): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(produit);
        return this.http
            .post<IProduit>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(produit: IProduit): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(produit);
        return this.http
            .put<IProduit>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IProduit>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IProduit[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(produit: IProduit): IProduit {
        const copy: IProduit = Object.assign({}, produit, {
            mm: produit.mm != null && produit.mm.isValid() ? produit.mm.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.mm = res.body.mm != null ? moment(res.body.mm) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((produit: IProduit) => {
                produit.mm = produit.mm != null ? moment(produit.mm) : null;
            });
        }
        return res;
    }
}
