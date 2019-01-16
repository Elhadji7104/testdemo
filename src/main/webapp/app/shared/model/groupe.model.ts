import { IUtilisateur } from 'app/shared/model//utilisateur.model';

export interface IGroupe {
    id?: number;
    groupeName?: string;
    users?: IUtilisateur[];
}

export class Groupe implements IGroupe {
    constructor(public id?: number, public groupeName?: string, public users?: IUtilisateur[]) {}
}
