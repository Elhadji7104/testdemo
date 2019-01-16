import { IGroupe } from 'app/shared/model//groupe.model';
import { IProduit } from 'app/shared/model//produit.model';

export interface IUtilisateur {
    id?: number;
    idUser?: number;
    userName?: string;
    users?: IGroupe[];
    enprunters?: IProduit[];
}

export class Utilisateur implements IUtilisateur {
    constructor(
        public id?: number,
        public idUser?: number,
        public userName?: string,
        public users?: IGroupe[],
        public enprunters?: IProduit[]
    ) {}
}
