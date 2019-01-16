import { IProduit } from 'app/shared/model//produit.model';

export interface IFournisseur {
    id?: number;
    fournisseurName?: string;
    achats?: IProduit[];
}

export class Fournisseur implements IFournisseur {
    constructor(public id?: number, public fournisseurName?: string, public achats?: IProduit[]) {}
}
