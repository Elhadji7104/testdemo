import { Moment } from 'moment';
import { IUtilisateur } from 'app/shared/model//utilisateur.model';
import { IFournisseur } from 'app/shared/model//fournisseur.model';

export interface IProduit {
    id?: number;
    cas?: string;
    quantite?: string;
    lieu?: string;
    nom?: string;
    mm?: Moment;
    risque?: string;
    molecule?: string;
    users?: IUtilisateur[];
    achats?: IFournisseur[];
}

export class Produit implements IProduit {
    constructor(
        public id?: number,
        public cas?: string,
        public quantite?: string,
        public lieu?: string,
        public nom?: string,
        public mm?: Moment,
        public risque?: string,
        public molecule?: string,
        public users?: IUtilisateur[],
        public achats?: IFournisseur[]
    ) {}
}
