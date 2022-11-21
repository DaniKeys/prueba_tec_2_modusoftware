//Capacidad -> 5 Agregar stock de un libro
pm.test("Retorno de atributos tabla libro", function () {
    pm.expect(pm.response.text()).to.include("id");
    pm.expect(pm.response.text()).to.include("nombre");
    pm.expect(pm.response.text()).to.include("autor");
    pm.expect(pm.response.text()).to.include("editorial");
    pm.expect(pm.response.text()).to.include("fecha");
    pm.expect(pm.response.text()).to.include("numeroPag");
    pm.expect(pm.response.text()).to.include("stock");
    pm.expect(pm.response.text()).to.include("valor");
    pm.expect(pm.response.text()).to.include("isbn");
});

pm.test("Validar todos los status", function () {
pm.expect(pm.response.code).to.be.oneOf([201,204,304,400,404,405,500]);
});

const jsonData = pm.response.json();
pm.test("Validar tipo de dato", () => {

  pm.expect(jsonData.id).to.be.a("number");
  pm.expect(jsonData.nombre).to.be.a("string");
  pm.expect(jsonData.valor).to.be.an("number");
    pm.expect(jsonData.stock).to.be.an("number");

});

pm.test("Tiempo de respuesta es menor a 1000ms", () => {
  pm.expect(pm.response.responseTime).to.be.below(1000);
});

pm.test("Si la peticion y respuesta contienen Content-Type ", () => {
  pm.request.to.have.header("Content-Type");
  pm.response.to.have.header("Content-Type");
});

pm.test("Validar formato del Content-Type de peticion y respuesta", () => {
  pm.expect(pm.request.headers.get('Content-Type')).to.eql('application/json');
  pm.expect(pm.response.headers.get('Content-Type')).to.eql('application/json');
});

