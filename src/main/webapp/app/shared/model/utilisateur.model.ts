import { IGroupe } from 'app/shared/model//groupe.model';

export interface IUtilisateur {
    id?: number;
    idUser?: number;
    userName?: string;
    groupe?: IGroupe;
}

export class Utilisateur implements IUtilisateur {
    constructor(public id?: number, public idUser?: number, public userName?: string, public groupe?: IGroupe) {}
}
