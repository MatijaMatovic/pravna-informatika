import "./VerdictPreview.css";

interface VerdictPreviewParams {
    verdict: CaseDescription
}

export default function VerdictPreview({ verdict }: VerdictPreviewParams) {
  return (
    <div className="card mt-2">
      <div className="card-body">
        <h5 className="card-title mb-3">
          {verdict.poslovniBroj}
          {" | "}
          <span>
            <small>{verdict.sud}</small>
          </span>
          {" | "}
          <span>
            <small>Sudija {verdict.sudija}</small>
          </span>
        </h5>
        <p className="card-text">
          <span className="judgement-tag">{verdict.krivicnoDelo}</span>
          <span className="judgement-tag">
            Supstanca: {verdict.supstanca}
          </span>
          {verdict.prodaje === "da" && (
            <span className="judgement-tag">Prodaje</span>
          )}
          {verdict.proizvodi === "da" && (
            <span className="judgement-tag">Proizvodi</span>
          )}
          {verdict.poseduje === "da" && (
            <span className="judgement-tag">Poseduje</span>
          )}
          {verdict.posreduje === "da" && (
            <span className="judgement-tag">Posreduje</span>
          )}
          {verdict.usao_u_zemlju === "da" && (
            <span className="judgement-tag">Uneo</span>
          )}
          {verdict.organizuje_mrezu === "da" && (
            <span className="judgement-tag">Organizuje O.K.G.</span>
          )}
          {verdict.prodaje_ugrozenim === "da" && (
            <span className="judgement-tag">Prodaje ugrozenim grupama</span>
          )}
          {verdict.is_sluzbeno_lice === "da" && (
            <span className="judgement-tag">Zloupotreba polozaja za stavljanje u promet</span>
          )}
          {(verdict.obezbedjuje_materijal === "da" || verdict.obezbedjuje_supstancu) && (
            <span className="judgement-tag">Obezbedjuje materijal/supstancu</span>
          )}
          {verdict.drukao === "da" && (
            <span className="judgement-tag">Svedok saradnik</span>
          )}
        </p>
        <a href={`verdicts/${verdict.poslovniBroj}`} className="btn btn-primary">
          Detaljnije
        </a>
      </div>
    </div>
  );
}
