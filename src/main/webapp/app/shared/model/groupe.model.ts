import { IUtilisateur } from 'app/shared/model//utilisateur.model';

export interface IGroupe {
    id?: number;
    groupeName?: string;
    appartients?: IUtilisateur[];
}

export class Groupe implements IGroupe {
    constructor(public id?: number, public groupeName?: string, public appartients?: IUtilisateur[]) {}
}
